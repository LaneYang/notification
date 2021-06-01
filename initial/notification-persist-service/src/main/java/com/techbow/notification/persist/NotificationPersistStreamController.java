package com.techbow.notification.persist;

import com.techbow.notification.data.Notification;
import com.techbow.notification.data.ProcessContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
@EnableBinding(NotificationPersistStreamProcessor.class)
public class NotificationPersistStreamController {
    private static final Logger LOG = Logger.getLogger(NotificationPersistStreamController.class.getName());

    @Autowired
    private NotificationCRUDService CRUDService;

    @Autowired
    private NotificationPersistStreamProcessor processor;

    @StreamListener(NotificationPersistStreamProcessor.PERSIST_CHANNEL)
    public void processPersistEvent(ProcessContext processContext) {
        LOG.info("Persist notification for process context: " + processContext.toString());

        Notification notification = processContext.getNotification();
        if (notification.getId() != null) {
            notification = CRUDService.saveNotification(notification);
        } else {
            notification = CRUDService.createNotification(notification);
        }
        processContext.setNotification(notification);
        processor.processChannel().send(
                MessageBuilder.withPayload(processContext).build()
        );
    }
}
