package com.example.pasteservice.dto;

import com.example.pasteservice.model.enums.Access;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetPasteDto(String name,

                          String body,

                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                          LocalDateTime creationTime,

                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                          LocalDateTime expiredTime,

                          Access access) {

}
