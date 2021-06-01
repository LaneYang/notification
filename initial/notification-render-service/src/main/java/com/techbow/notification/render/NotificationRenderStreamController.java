package com.techbow.notification.render;

import com.techbow.notification.data.Notification;
import com.techbow.notification.data.ProcessContext;
import com.techbow.notification.data.RenderedEmail;
import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
@EnableBinding(NotificationRenderStreamProcessor.class)
public class NotificationRenderStreamController {
    private static final Logger LOG = Logger.getLogger(NotificationRenderStreamController.class.getName());

    @Autowired
    private NotificationRenderStreamProcessor processor;

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private UserService userService;

    @StreamListener(NotificationRenderStreamProcessor.RENDER_CHANNEL)
    public void processEvent(ProcessContext processContext) {
        LOG.info("Render processContext: " + processContext.toString());

        Tracker tracker = processContext.getTracker();
        tracker.setState(Tracker.State.Rendered);
        trackerService.saveTracker(tracker);

        processContext.setRenderedContent(
                renderNotificationAsEmail(processContext.getNotification())
        );

        processor.deliverChannel().send(
                MessageBuilder.withPayload(processContext).build()
        );
    }

    private RenderedEmail renderNotificationAsEmail(Notification notification) {
        RenderedEmail email = new RenderedEmail();
        email.setFrom(userService.getUserEmail(notification.getSenderId()));
        email.setTo(userService.getUserEmail(notification.getReceiverId()));
        email.setSubject(notification.getTitle());
        email.setMessage(notification.getSubtitle());
        return email;
    }
}