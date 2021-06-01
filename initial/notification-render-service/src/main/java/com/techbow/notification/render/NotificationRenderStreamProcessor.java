package com.techbow.notification.render;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotificationRenderStreamProcessor {
    String RENDER_CHANNEL = "render";
    String DELIVER_CHANNEL = "deliver";

    @Input(RENDER_CHANNEL)
    MessageChannel renderChannel();

    @Output(DELIVER_CHANNEL)
    MessageChannel deliverChannel();
}
