package com.techbow.notification.scheduler;

import com.techbow.notification.data.ProcessContext;
import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

@Service
@EnableBinding(NotificationSchedulerStreamProcessor.class)
public class ScheduledSendTaskQueue {
    public static final Integer QUEUE_SIZE = 128;

    private static final Logger LOG = Logger.getLogger(ScheduledSendTaskQueue.class.getName());
    private Queue<ProcessContext> sendQueue = new LinkedList<>();

    @Autowired
    private NotificationSchedulerStreamProcessor streamProcessor;

    public Integer getAvailableQueueSize() {
        return QUEUE_SIZE - sendQueue.size();
    }

    public Boolean enqueueProcessContext(ProcessContext processContext) {
        if (sendQueue.size() >= QUEUE_SIZE) {
            return false;
        }
        sendQueue.offer(processContext);
        return true;
    }

    @Scheduled(fixedRate = 200)
    public void scheduledSend() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        while (sendQueue.size() > 0) {
            ProcessContext processContext = sendQueue.peek();
            Tracker tracker = processContext.getTracker();
            if (tracker.getScheduledTimestamp().after(currentTimestamp)) {
                tracker.setState(Tracker.State.Initialized);

                streamProcessor.processChannel().send(
                        MessageBuilder.withPayload(processContext).build()
                );
                sendQueue.poll();
            } else {
                break;
            }
        }
    }
}