package com.techbow.notification.process;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotificationProcessStreamProcessor {
    String PROCESS_CHANNEL = "process";
    String PERSIST_CHANNEL = "persist";
    String RENDER_CHANNEL = "render";

    @Input(PROCESS_CHANNEL)
    MessageChannel processChannel();

    @Output(PERSIST_CHANNEL)
    MessageChannel persistChannel();

    @Output(RENDER_CHANNEL)
    MessageChannel renderChannel();
}
