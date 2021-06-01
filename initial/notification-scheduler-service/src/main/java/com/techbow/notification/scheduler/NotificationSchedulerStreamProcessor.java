package com.techbow.notification.scheduler;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotificationSchedulerStreamProcessor {
    String PROCESS_CHANNEL = "process";

    @Output(PROCESS_CHANNEL)
    MessageChannel processChannel();
}
