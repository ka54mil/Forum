package com.example.auction.repositories;

import com.example.auction.models.Attachment;
import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAttachmentsByItem(Item item);
    Page<Attachment> findAttachmentsByItemIn(Pageable pageable, List<Item> item);
}
