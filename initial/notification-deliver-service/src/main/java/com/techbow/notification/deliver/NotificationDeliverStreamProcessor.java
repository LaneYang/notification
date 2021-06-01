package com.techbow.notification.deliver;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface NotificationDeliverStreamProcessor {
    String DELIVER_CHANNEL = "deliver";

    @Input(DELIVER_CHANNEL)
    MessageChannel deliverChannel();
}
