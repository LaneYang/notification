package com.techbow.notification.deliver;

import com.techbow.notification.data.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class TrackerService {
    private static final String URL = "http://tracker-persist-service";

    @Autowired
    private RestTemplate restTemplate;

    public Tracker createTracker() {
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl(URL).path("/tracker/create").build();
        return restTemplate.getForObject(uriComponents.toUriString(), Tracker.class);
    }

    public Tracker getTracker(Long id) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("id", Long.toString(id));
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl(URL).path("/tracker/{id}").buildAndExpand(urlParams);
        return restTemplate.getForObject(uriComponents.toUriString(), Tracker.class);
    }

    public Tracker saveTracker(Tracker tracker) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("id", Long.toString(tracker.getId()));
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl(URL).path("/tracker/{id}").buildAndExpand(urlParams);
        return restTemplate.postForObject(uriComponents.toUriString(), tracker, Tracker.class);
    }

    public List<Tracker> getTrackersWithState(Integer count, Tracker.State state) {
        UriComponents uriComponents =
                UriComponentsBuilder.fromHttpUrl(URL).path("/tracker").queryParam("state", state).build();
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(uriComponents.toUriString(), Tracker[].class)));
    }
}
