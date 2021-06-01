package com.techbow.notification.persist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "notification_seq")
public class NotificationSequence implements Serializable {
    private Long seq;

    public NotificationSequence(Long seq) {
        this.seq = seq;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}