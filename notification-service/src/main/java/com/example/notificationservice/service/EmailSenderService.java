package com.example.notificationservice.service;

import com.local.basedomains.event.PasteCreatedEvent;
import com.local.basedomains.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${pasteService.url}")
    private String pasteServiceUrl;

    public void sendUserCreatedEmail(UserCreatedEvent userCreatedEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(userCreatedEvent.email());
        message.setText("Thank you for using newpastebin, good luck and have fun!");
        message.setSubject("Hello from newpastebin, %s!"
                .formatted(userCreatedEvent.username()));

        mailSender.send(message);
        log.info("Message was sent to %s, with email %s"
                .formatted(userCreatedEvent.username(), userCreatedEvent.email()));
    }

    public void sendPasteCreatedEmail(PasteCreatedEvent pasteCreatedEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(pasteCreatedEvent.email());
        message.setText("You created new paste called %s. This paste is %s, users can find it by url: %s%s"
                .formatted(pasteCreatedEvent.pasteName(),
                        pasteCreatedEvent.access().toLowerCase(),
                        pasteServiceUrl,
                        pasteCreatedEvent.url()));
        message.setSubject("Hello from newpastebin, %s!".formatted(pasteCreatedEvent.username()));

        mailSender.send(message);
        log.info("Message was sent to %s, with email %s"
                .formatted(pasteCreatedEvent.username(), pasteCreatedEvent.email()));
    }
}
