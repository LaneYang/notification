package com.techbow.notification.tracker;

import com.techbow.notification.data.Tracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PersistedTracker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Tracker.State state;

    public PersistedTracker() {
        this.id = null;
        this.state = null;
    }

    public PersistedTracker(Tracker tracker) {
        this.id = tracker.getId();
        this.state = tracker.getState();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tracker.State getState() {
        return state;
    }

    public void setState(Tracker.State state) {
        this.state = state;
    }

    public Tracker toTracker() {
        Tracker tracker = new Tracker();
        tracker.setId(this.getId());
        tracker.setState(this.getState());
        return tracker;
    }
}