package com.techbow.notification.process;

import com.techbow.notification.data.Notification;
import com.techbow.notification.data.ProcessContext;
import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@RestController
public class NotificationProcessRestController {
    private static final Logger LOG = Logger.getLogger(NotificationProcessRestController.class.getName());

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private NotificationProcessStreamProcessor processor;

    @PostMapping("/send")
    private String processNotification(@RequestBody Notification notification) {
        LOG.info("Processing notification: " + notification.toString());

        ProcessContext processContext = new ProcessContext();

        // created state for tracker is initialized
        Tracker tracker = trackerService.createTracker();
        tracker.setState(Tracker.State.Initialized);

        // set scheduled timestamp to tracker
        if (notification.getDelay() != null) {
            Timestamp timestamp = new Timestamp(new Date().getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp.getTime());
            calendar.add(Calendar.SECOND, notification.getDelay().intValue());
            tracker.setScheduledTimestamp(new Timestamp(calendar.getTime().getTime()));
        }

        processContext.setTracker(tracker);

        processContext.setNotification(notification);

        processor.persistChannel().send(
                MessageBuilder.withPayload(processContext).build()
        );

        return "Success";
    }
}
