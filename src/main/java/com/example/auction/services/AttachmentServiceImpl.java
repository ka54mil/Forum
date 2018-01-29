package com.example.auction.services;

import com.example.auction.models.Attachment;
import com.example.auction.models.Item;
import com.example.auction.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment getById(Long id) {
        return attachmentRepository.findOne(id);
    }

    @Override
    public Page<Attachment> getAll(Pageable pageable) {
        return attachmentRepository.findAll(pageable);
    }

    @Override
    public List<Attachment> getAll() {
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment save(Attachment attachment) {
        if(attachment.getId() != null && attachment.getContent() == null){
            Attachment tmp = attachmentRepository.findOne(attachment.getId());
            attachment.setSuffix(tmp.getSuffix());
            attachment.setType(tmp.getType());
            attachment.setContent(tmp.getContent());
        }
        return attachmentRepository.save(attachment);
    }

    @Override
    public void delete(Long id) {
        attachmentRepository.delete(id);
    }

    @Override
    @Transactional
    public List<Attachment> getAttachmentsByItem(Item item) {
        return attachmentRepository.findAttachmentsByItem(item);
    }

    @Transactional
    @Override
    public Page<Attachment> getAttachmentsByItems(Pageable pageable, List<Item> item) {
        return attachmentRepository.findAttachmentsByItemIn(pageable,item);
    }
}
