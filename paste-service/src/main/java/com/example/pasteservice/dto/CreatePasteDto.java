package com.example.pasteservice.dto;

import com.example.pasteservice.model.Paste;
import com.example.pasteservice.model.enums.Access;
import com.example.pasteservice.model.enums.PasteDuration;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Random;

public record CreatePasteDto(@NotBlank
                             @Length(min = 3, max = 50, message = "Paste name must be longer then 3 and shorter then 30 symbols")
                             String name,

                             @NotBlank
                             @Length(min = 3, max = 255, message = "Paste text must be longer then 3 and shorter then 255 symbols")
                             String body,

                             PasteDuration pasteDuration,

                             Access access) {

    public Paste toPaste(Long userId) {
        Paste paste = new Paste();
        paste.setBody(this.body);
        paste.setName(this.name);
        paste.setExpiredTime(paste.getCreationTime().plus(pasteDuration.getDuration()));
        paste.setAccess(this.access);
        paste.setUserId(userId);
        paste.setUrl(generateRandomUrl());
        return paste;
    }

    private static String generateRandomUrl() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

}