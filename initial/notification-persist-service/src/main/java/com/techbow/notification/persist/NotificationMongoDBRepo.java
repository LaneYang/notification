package com.techbow.notification.persist;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMongoDBRepo extends MongoRepository<PersistedNotification, Long> {
}
