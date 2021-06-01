package com.techbow.notification.deliver;

import com.techbow.notification.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
@EnableBinding(NotificationDeliverStreamProcessor.class)
public class NotificationDeliverStreamController {
    private static final Logger LOG = Logger.getLogger(NotificationDeliverStreamController.class.getName());

    @Autowired
    private NotificationDeliverStreamProcessor processor;

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private EmailDeliverService emailDeliverService;

    @StreamListener(NotificationDeliverStreamProcessor.DELIVER_CHANNEL)
    public void processEvent(ProcessContext processContext) {
        LOG.info("Deliver processContext: " + processContext.toString());

        RenderedContent renderedContent = processContext.getRenderedContent();
        if (renderedContent instanceof RenderedEmail) {
            emailDeliverService.sendEmail((RenderedEmail) renderedContent);
        } else if (renderedContent instanceof RenderedPush) {
            // send push
        }

        Tracker tracker = processContext.getTracker();
        tracker.setState(Tracker.State.Delivered);
        trackerService.saveTracker(tracker);
    }
}