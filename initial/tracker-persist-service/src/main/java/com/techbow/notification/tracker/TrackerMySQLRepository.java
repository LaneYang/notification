package com.techbow.notification.tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerMySQLRepository extends JpaRepository<PersistedTracker, Long> {
}
