package com.techbow.notification.scheduler;

import com.techbow.notification.data.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationPersistService {
    private static final String URL = "http://notification-persist-service";

    @Autowired
    private RestTemplate restTemplate;

    public List<Notification> getNotifications(List<Long> ids) {
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl(URL).path("/notification/batch").build();
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(uriComponents.toUriString(), ids, Notification[].class)));
    }
}
