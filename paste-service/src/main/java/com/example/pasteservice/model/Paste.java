package com.example.pasteservice.model;

import com.example.pasteservice.dto.GetPasteDto;
import com.example.pasteservice.model.enums.Access;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paste")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(name = "expired_time", nullable = false)
    private LocalDateTime expiredTime;

    @Column(name = "access", nullable = false)
    @Enumerated(EnumType.STRING)
    private Access access;

    @Column(name = "user_id")
    private Long userId;

    public GetPasteDto toGetDto(){
        return GetPasteDto.builder()
                .name(this.getName())
                .body(this.getBody())
                .creationTime(this.getCreationTime())
                .expiredTime(this.getExpiredTime())
                .access(this.getAccess())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paste paste)) return false;
        return name.equals(paste.name) && body.equals(paste.body) && url.equals(paste.url) && creationTime.equals(paste.creationTime) && expiredTime.equals(paste.expiredTime) && access == paste.access && userId.equals(paste.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, body, url, creationTime, expiredTime, access, userId);
    }
}