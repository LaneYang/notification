package com.techbow.notification.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

public class Tracker implements Serializable {
   public enum State {
        @JsonProperty("none")
        None,
        @JsonProperty("initialized")
        Initialized,
        @JsonProperty("persisted")
        Persisted,
        @JsonProperty("rendered")
        Rendered,
        @JsonProperty("delivered")
        Delivered,
        @JsonProperty("delayed")
        Delayed,
        @JsonProperty("scheduled")
        Scheduled,
    }

    private Long id;
    private Long notificationId;
    private State state;
    private Timestamp scheduledTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Timestamp getScheduledTimestamp() {
        return scheduledTimestamp;
    }

    public void setScheduledTimestamp(Timestamp scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }
}