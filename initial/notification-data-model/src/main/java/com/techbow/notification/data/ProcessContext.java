package com.techbow.notification.data;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.io.Serializable;

public class ProcessContext implements Serializable {
    private Tracker tracker;
    private Notification notification;
    private RenderedContent renderedContent;

    public ProcessContext() {
        this.tracker = null;
        this.notification = null;
        this.renderedContent = null;
    }

    public ProcessContext(Tracker tracker, Notification notification) {
        this.tracker = tracker;
        this.notification = notification;
        this.renderedContent = null;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public RenderedContent getRenderedContent() {
        return renderedContent;
    }

    public void setRenderedContent(RenderedContent renderedContent) {
        this.renderedContent = renderedContent;
    }
}
