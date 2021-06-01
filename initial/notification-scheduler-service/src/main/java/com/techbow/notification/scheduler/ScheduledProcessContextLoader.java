package com.techbow.notification.scheduler;

import com.techbow.notification.data.Notification;
import com.techbow.notification.data.ProcessContext;
import com.techbow.notification.data.Tracker;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduledProcessContextLoader {
    private static final Logger LOG = Logger.getLogger(ScheduledProcessContextLoader.class.getName());

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private NotificationPersistService persistService;

    @Autowired
    private ScheduledSendTaskQueue sendTaskQueue;

    @GetMapping("/trigger")
    public String triggerLoad() {
        this.fetchScheduledNotifications();
        return "Scheduled notification successfully triggered";
    }

    @Scheduled(fixedRate = 5000)
    public void fetchScheduledNotifications() {
        LOG.info("Fetch scheduled notifications");

        List<Tracker> trackers = trackerService.getTrackersWithState(
                sendTaskQueue.getAvailableQueueSize(),
                Tracker.State.Delayed
        );

        List<Notification> notifications = persistService.getNotifications(
                trackers.stream().map(Tracker::getId).collect(Collectors.toList())
        );
        Map<Long, Notification> notificationMap = notifications.stream()
                .collect(Collectors.toMap(Notification::getId, notification -> notification));

        List<ProcessContext> processContexts = trackers.stream()
                .filter(tracker -> notificationMap.containsKey(tracker.getId()))
                .map(tracker -> new ProcessContext(tracker, notificationMap.get(tracker.getId())))
                .collect(Collectors.toList());

        processContexts.forEach(
                processContext -> sendTaskQueue.enqueueProcessContext(processContext)
        );
    }
}