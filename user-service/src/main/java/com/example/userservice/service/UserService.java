package com.example.userservice.service;

import com.example.userservice.client.PasteClient;
import com.example.userservice.dto.CreateUserDto;
import com.example.userservice.dto.GetUserDto;
import com.example.userservice.handler.exception.ResourceNotFoundException;
import com.example.userservice.handler.exception.UniqueConstraintViolatedException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.local.basedomains.event.UserCreatedEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    private final PasteClient pasteClient;

    @Transactional
    public void createUser(@Valid CreateUserDto userDto) {
        if (userRepository.existsUserByEmail(userDto.email())) {
            throw new UniqueConstraintViolatedException("User with email " + userDto.email() + " already exists");
        } else if (userRepository.existsUserByUsername(userDto.username())) {
            throw new UniqueConstraintViolatedException("User with username " + userDto.username() + " already exists");
        } else {
            User user = userDto.toUser();
            userRepository.save(user);

            UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
            String topic = "UserCreatedTopic";
            kafkaTemplate.send(topic, userCreatedEvent);
            log.info(String.format("Message sent -> " + userCreatedEvent + " to topic -> " + topic));
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            pasteClient.deleteAllByUserId(id);
        } else {
            throw new ResourceNotFoundException("User with id " + id + " doesn't exist");
        }
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(User::toGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(@Valid CreateUserDto userDto, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " doesn't exist")
        );
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        userRepository.save(user);
    }

    public GetUserDto getUserById(Long userId) {
        var user = userRepository.findById(userId).orElse(null);
        return user != null ? user.toGetDto() : null;
    }
}