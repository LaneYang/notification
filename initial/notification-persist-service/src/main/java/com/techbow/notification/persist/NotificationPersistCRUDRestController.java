package com.techbow.notification.persist;

import com.techbow.notification.data.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")
public class NotificationPersistCRUDRestController {
    @Autowired
    private NotificationCRUDService CRUDService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return CRUDService.getAllNotifications();
    }

    @PostMapping("/batch")
    public List<Notification> getNotificationWithIds(@RequestBody List<Long> notificationIds) {
        return notificationIds.stream().map(this::getNotification).collect(Collectors.toList());
    }

    @PostMapping
    public Notification saveNotification(@RequestBody Notification notification) {
        if (notification.getId() != null) {
            return CRUDService.saveNotification(notification);
        }
        return CRUDService.createNotification(notification);
    }

    @GetMapping("/{id}")
    Notification getNotification(@PathVariable Long id) {
        return CRUDService.getNotification(id);
    }

    @DeleteMapping("/{id}")
    void deleteNotification(@PathVariable Long id) {
        Notification notification = CRUDService.getNotification(id);
        if (notification == null) {
            return;
        }
        notification.setDeleted(true);
        CRUDService.saveNotification(notification);
    }
}
