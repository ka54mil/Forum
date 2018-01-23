package com.example.auction.services;

import com.example.auction.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    boolean isUniqueCategory(String Category);
    Page<Category> getAll(Pageable pageable);
    Page<Category> getAllActive(Pageable pageable);
    Category getById(Long id);


}
