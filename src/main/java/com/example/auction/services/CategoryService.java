package com.example.auction.services;

import com.example.auction.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService extends Service<Category> {

    boolean isUniqueCategory(Category category);
    Page<Category> getAllActive(Pageable pageable);
    List<Category> getAllActive();


}
