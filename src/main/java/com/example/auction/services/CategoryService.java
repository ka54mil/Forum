package com.example.auction.services;

import com.example.auction.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService extends Service<Category> {

    boolean isUniqueCategory(String Category);
    Page<Category> getAllActive(Pageable pageable);


}
