package com.techbow.notification.persist;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotificationPersistStreamProcessor {
    String PERSIST_CHANNEL = "persist";
    String PROCESS_CHANNEL = "process";

    @Input(PERSIST_CHANNEL)
    MessageChannel persistChannel();

    @Output(PROCESS_CHANNEL)
    MessageChannel processChannel();

}
