package com.example.pasteservice.service;

import com.example.pasteservice.client.ClientUserDto;
import com.example.pasteservice.repository.PasteRepository;
import com.example.pasteservice.client.UserClient;
import com.example.pasteservice.dto.CreatePasteDto;
import com.example.pasteservice.dto.GetPasteDto;
import com.example.pasteservice.handler.exception.ResourceNotFoundException;
import com.example.pasteservice.model.Paste;
import com.example.pasteservice.model.enums.Access;
import com.local.basedomains.event.PasteCreatedEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
@EnableScheduling
public class PasteService {

    private final PasteRepository pasteRepository;
    private final KafkaTemplate<String, PasteCreatedEvent> kafkaTemplate;
    private final UserClient userClient;

    @Value("${pasteService.address}")
    private String address;

    public GetPasteDto getPasteByUrl(String url) {
        var paste = pasteRepository.findPasteByUrl(url).orElseThrow(
                () -> new ResourceNotFoundException("Paste by URL " + url + " doesn't exist"));
        return paste.toGetDto();
    }

    public List<GetPasteDto> getPublicPastes() {
        return pasteRepository.findTop10ByAccessOrderByCreationTimeDesc(Access.PUBLIC).stream()
                .map(Paste::toGetDto)
                .collect(Collectors.toList());
    }

    public List<GetPasteDto> getUserPublicPastes(Long userId){
        ClientUserDto user = userClient.getUser(userId);
        if(user == null){
            throw new ResourceNotFoundException("User with id " + userId + " doesn't exists");
        } else {
            return pasteRepository.findAllByUserIdAndAccessOrderByCreationTimeDesc(userId, Access.PUBLIC)
                    .stream()
                    .map(Paste::toGetDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public String createPaste(@Valid CreatePasteDto pasteDto, Long userId) {
        ClientUserDto user = userClient.getUser(userId);
        if(user == null){
            throw new ResourceNotFoundException("User with id " + userId + " doesn't exists");
        } else {
            Paste paste = pasteDto.toPaste(userId);
            pasteRepository.save(paste);

            PasteCreatedEvent pasteCreatedEvent = PasteCreatedEvent.builder()
                    .username(user.username())
                    .email(user.email())
                    .pasteName(paste.getName())
                    .url(paste.getUrl())
                    .access(String.valueOf(paste.getAccess()))
                    .build();
            kafkaTemplate.send("PasteCreatedTopic", pasteCreatedEvent);
            log.info("Message sent -> " + pasteCreatedEvent);
            return "Paste created and available on: " + address + paste.getUrl();
        }
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        pasteRepository.deleteAllByUserId(userId);
    }

    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void deleteExpiredPastes(){
        pasteRepository.deleteAllByExpiredTimeIsBefore(LocalDateTime.now());
    }

}
