package com.techbow.notification.persist;

import com.techbow.notification.data.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationCRUDService {
    @Autowired
    private NotificationSequenceService sequenceService;

    @Autowired
    private NotificationRedisService notificationCache;

    @Autowired
    private NotificationMongoDBRepo notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll().stream().map(PersistedNotification::toNotification).collect(Collectors.toList());
    }

    public Notification createNotification(Notification notification) {
        PersistedNotification persisted = new PersistedNotification(notification);
        persisted.setId(sequenceService.getNextSequence("notification_seq"));
        persisted = notificationRepository.save(persisted);
        notificationCache.save(persisted);
        return persisted.toNotification();
    }

    public Notification getNotification(Long id) {
        PersistedNotification notification = notificationCache.findById(id);
        if (notification != null) {
            return notification.toNotification();
        }
        notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notificationCache.save(notification);
        }
        return notification.toNotification();
    }

    public Notification saveNotification(Notification notification) {
        PersistedNotification persisted = new PersistedNotification(notification);
        persisted = notificationRepository.save(persisted);
        notificationCache.save(persisted);
        return persisted.toNotification();
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
        notificationCache.deleteById(id);
    }
}
