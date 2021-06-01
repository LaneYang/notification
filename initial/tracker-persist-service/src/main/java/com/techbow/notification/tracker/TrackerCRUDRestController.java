package com.techbow.notification.tracker;

import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tracker")
public class TrackerCRUDRestController {
    private static final Logger LOG = Logger.getLogger(TrackerCRUDRestController.class.getName());

    @Autowired
    private TrackerPersistService trackerService;

    @GetMapping("/create")
    public Tracker createTrackers() {
        LOG.info("Create Tracker");
        return trackerService.saveTracker(new Tracker());
    }

    @GetMapping
    public List<Tracker> getAllTracker(@RequestParam(required = false) Tracker.State state) {
        return trackerService.getAllTrackers(state);
    }

    @PostMapping("/{id}")
    public Tracker saveTracker(@RequestBody @NotNull Tracker tracker) {
        LOG.info("Save Tracker: " + tracker.toString());

        if (tracker.getState() == Tracker.State.None) {
            tracker.setState(Tracker.State.Persisted);
        }
        return trackerService.saveTracker(tracker);
    }

    @PutMapping("/{id}")
    public Tracker updateTracker(@PathVariable Long id, @RequestBody Tracker tracker) {
        LOG.info("Update Tracker: " + tracker);
        return trackerService.saveTracker(tracker);
    }

    @GetMapping("/{id}")
    public Tracker getTracker(@PathVariable @NotNull Long id) {
        LOG.info("Get Tracker with id: " + id.toString());
        return trackerService.getTracker(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTracker(@PathVariable @NotNull Long id) {
        LOG.info("Delete Tracker with id: " + id.toString());
        trackerService.deleteTracker(id);
    }
}
