package com.techbow.notification.persist;

import com.techbow.notification.data.Notification;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "notification")
public class PersistedNotification implements Serializable {

    @Id
    private Long id;

    private int priority;
    private Long senderId;
    private Long receiverId;

    private String title;
    private String subtitle;
    private String content;

    private boolean deleted;

    public PersistedNotification() {
    }

    public PersistedNotification(Long id) {
        this.id = id;
    }

    public PersistedNotification(Notification notification) {
        this.id = notification.getId();

        this.priority = notification.getPriority();
        this.senderId = notification.getSenderId();
        this.receiverId = notification.getReceiverId();

        this.title = notification.getTitle();
        this.subtitle = notification.getSubtitle();
        this.content = notification.getContent();

        this.deleted = notification.isDeleted();
    }

    public Notification toNotification() {
        Notification notification = new Notification();
        notification.setId(this.getId());

        notification.setPriority(this.getPriority());
        notification.setSenderId(this.getSenderId());
        notification.setReceiverId(this.getReceiverId());

        notification.setTitle(this.getTitle());
        notification.setSubtitle(this.getSubtitle());
        notification.setContent(this.getContent());

        notification.setDeleted(this.isDeleted());
        return notification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
