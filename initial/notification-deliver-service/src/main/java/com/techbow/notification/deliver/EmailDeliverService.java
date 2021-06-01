package com.techbow.notification.deliver;

import com.techbow.notification.data.RenderedEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailDeliverService {
    private static final Logger LOG = Logger.getLogger(EmailDeliverService.class.getName());

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(RenderedEmail email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setFrom(email.getFrom());
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());
        LOG.info("Send: " + message.toString());
        javaMailSender.send(message);
    }
}
