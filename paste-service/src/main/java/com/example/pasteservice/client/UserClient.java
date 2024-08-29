package com.example.pasteservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping(value = "/{userId}")
    ClientUserDto getUser(@PathVariable Long userId);
}
