package com.example.auction.services;

import com.example.auction.models.Attachment;
import com.example.auction.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return attachmentRepository.save(attachment);
    }

    @Override
    public long countAll(){
        return attachmentRepository.count();
    }

    @Override
    public boolean isAttachmentExist(String path){
        return attachmentRepository.findAttachmentByPath(path) != null;
    }

    @Override
    public void delete(Long id) {
        attachmentRepository.delete(id);
    }
}
