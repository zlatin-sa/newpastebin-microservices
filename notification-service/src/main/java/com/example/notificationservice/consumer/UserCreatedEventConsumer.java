package com.example.notificationservice.consumer;

import com.example.notificationservice.service.EmailSenderService;
import com.local.basedomains.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCreatedEventConsumer {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "UserCreatedTopic", groupId = "notification-group")
    public void consumeUser(UserCreatedEvent userCreatedEvent) {
        emailSenderService.sendUserCreatedEmail(userCreatedEvent);
        log.info("Message received -> %s from topic -> UsersCreatedTopic"
                .formatted(userCreatedEvent));
    }
}
