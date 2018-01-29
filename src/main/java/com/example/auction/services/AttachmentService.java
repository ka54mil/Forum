package com.example.auction.services;

import com.example.auction.models.Attachment;
import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttachmentService extends Service<Attachment> {
    List<Attachment> getAttachmentsByItem(Item item);
    Page<Attachment> getAttachmentsByItems(Pageable pageable, List<Item> item);
}
