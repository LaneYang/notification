package com.techbow.notification.process;

import com.techbow.notification.data.Notification;
import com.techbow.notification.data.ProcessContext;
import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
@EnableBinding(NotificationProcessStreamProcessor.class)
public class NotificationProcessStreamController {
    private static final Logger LOG = Logger.getLogger(NotificationProcessStreamController.class.getName());

    @Autowired
    private NotificationProcessStreamProcessor streamProcessor;

    @Autowired
    private TrackerService trackerService;

    @StreamListener(NotificationProcessStreamProcessor.PROCESS_CHANNEL)
    public void processEvent(ProcessContext processContext) {
        LOG.info("Continue process processContext: " + processContext.toString());

        Tracker tracker = processContext.getTracker();
        Notification notification = processContext.getNotification();
        if (tracker.getState() == Tracker.State.Initialized) {
            tracker.setState(Tracker.State.Persisted);
            tracker.setNotificationId(notification.getId());
            trackerService.saveTracker(tracker);

            // send notification to renderer
            streamProcessor.renderChannel().send(
                    MessageBuilder.withPayload(processContext).build()
            );
        } else if (tracker.getState() == Tracker.State.Scheduled) {
            // send notification to renderer
            streamProcessor.renderChannel().send(
                    MessageBuilder.withPayload(processContext).build()
            );
        }
    }
}