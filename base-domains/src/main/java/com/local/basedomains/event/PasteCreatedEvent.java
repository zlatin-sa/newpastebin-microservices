package com.local.basedomains.event;

import lombok.Builder;

@Builder
public record PasteCreatedEvent(String username,
                                String email,
                                String pasteName,
                                String url,
                                String access) {
}
