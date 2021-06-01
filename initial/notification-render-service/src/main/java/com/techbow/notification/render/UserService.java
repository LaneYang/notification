package com.techbow.notification.render;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String getUserEmail(Long userId) {
        return "philsong.tb@techbow.com";
    }
}
