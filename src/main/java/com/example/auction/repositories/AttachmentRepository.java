package com.example.auction.repositories;

import com.example.auction.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findAttachmentByPath(String path);
}
