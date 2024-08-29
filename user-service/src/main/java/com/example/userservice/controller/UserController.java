package com.example.userservice.controller;

import com.example.userservice.dto.CreateUserDto;
import com.example.userservice.dto.GetUserDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public List<GetUserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/create")
    public void createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/update/{id}")
    public void updateUser(@RequestBody CreateUserDto dto, @PathVariable Long id) {
        userService.updateUser(dto, id);
    }

    @GetMapping("/{userId}")
    public GetUserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}