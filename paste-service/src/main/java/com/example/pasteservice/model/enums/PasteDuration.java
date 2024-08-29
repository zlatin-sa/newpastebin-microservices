package com.example.pasteservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum PasteDuration {
    TEN_SEC(Duration.ofSeconds(10)),
    ONE_MIN(Duration.ofMinutes(1)),
    TEN_MIN(Duration.ofMinutes(10)),
    ONE_HOUR(Duration.ofHours(1)),
    THREE_HOUR(Duration.ofHours(3)),
    ONE_DAY(Duration.ofDays(1)),
    ONE_WEEK(Duration.ofDays(7)),
    ONE_MONTH(Duration.ofDays(30)),
    INFINITY(Duration.ofDays(10000));

    private final Duration duration;

}