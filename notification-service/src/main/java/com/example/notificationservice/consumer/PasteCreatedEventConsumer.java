package com.example.notificationservice.consumer;

import com.example.notificationservice.service.EmailSenderService;
import com.local.basedomains.event.PasteCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasteCreatedEventConsumer {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "PasteCreatedTopic", groupId = "notification-group")
    public void consumePaste(PasteCreatedEvent pasteCreatedEvent) {
        emailSenderService.sendPasteCreatedEmail(pasteCreatedEvent);
        log.info("Message received -> %s from topic -> PastesCreatedTopic"
                .formatted(pasteCreatedEvent));
    }

}
