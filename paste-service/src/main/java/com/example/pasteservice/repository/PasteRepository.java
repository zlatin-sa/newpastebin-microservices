package com.example.pasteservice.repository;

import com.example.pasteservice.model.Paste;
import com.example.pasteservice.model.enums.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Optional<Paste> findPasteByUrl(String url);
    List<Paste> findTop10ByAccessOrderByCreationTimeDesc(Access access);
    void deleteAllByExpiredTimeIsBefore(LocalDateTime time);
    void deleteAllByUserId(Long userId);
    List<Paste> findAllByUserIdAndAccessOrderByCreationTimeDesc(Long userId, Access access);
}
