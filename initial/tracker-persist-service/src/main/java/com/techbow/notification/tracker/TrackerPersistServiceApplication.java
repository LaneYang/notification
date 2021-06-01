package com.techbow.notification.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TrackerPersistServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrackerPersistServiceApplication.class, args);
    }
}
