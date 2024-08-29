package com.example.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("paste-service")
public interface PasteClient {
    @DeleteMapping("/delete/{userId}")
    void deleteAllByUserId(@PathVariable Long userId);
}
