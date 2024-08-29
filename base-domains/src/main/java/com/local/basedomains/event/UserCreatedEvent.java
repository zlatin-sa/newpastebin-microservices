package com.local.basedomains.event;

import lombok.Builder;

@Builder
public record UserCreatedEvent(String username,
                               String email) {
}
