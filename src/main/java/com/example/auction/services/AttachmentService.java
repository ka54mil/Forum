package com.example.auction.services;

import com.example.auction.models.Attachment;
import com.example.auction.models.Item;

import java.util.List;

public interface AttachmentService extends Service<Attachment> {
    List<Attachment> getAttachmentsByItem(Item item);
}
