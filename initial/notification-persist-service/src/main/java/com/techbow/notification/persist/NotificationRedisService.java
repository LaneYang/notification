package com.techbow.notification.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotificationRedisService {
    static final String NOTIFICATION_KEY = "notification";

    @Autowired
    private RedisTemplate<Long, PersistedNotification> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, PersistedNotification> hashOperations;

    public void save(PersistedNotification notification) {
        hashOperations.put(
                NOTIFICATION_KEY,
                notification.getId(),
                notification
        );
    }

    public PersistedNotification findById(Long id) {
        return hashOperations.get(NOTIFICATION_KEY, id);
    }

    public void deleteById(Long id) {
        hashOperations.delete(NOTIFICATION_KEY, id);
    }
}
