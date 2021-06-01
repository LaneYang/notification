package com.techbow.notification.tracker;

import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackerPersistService {
    @Autowired
    private TrackerMySQLRepository trackerRepository;

    public List<Tracker> getAllTrackers() {
        return trackerRepository.findAll().stream()
                .map(PersistedTracker::toTracker)
                .collect(Collectors.toList());
    }

    public List<Tracker> getAllTrackers(Tracker.State state) {
        if (state == null) {
            return trackerRepository.findAll().stream().map(PersistedTracker::toTracker).collect(Collectors.toList());
        }
        return trackerRepository.findAll().stream()
                .filter(tracker -> tracker.getState() == state)
                .map(PersistedTracker::toTracker)
                .collect(Collectors.toList());
    }

    public Tracker saveTracker(Tracker tracker) {
        return trackerRepository.save(new PersistedTracker(tracker)).toTracker();

    }

    public Tracker getTracker(Long id) {
        return trackerRepository.findById(id).orElse(null).toTracker();
    }

    public void deleteTracker(Long id) {
        trackerRepository.deleteById(id);
    }
}
