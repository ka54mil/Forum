package com.example.auction.services;

import com.example.auction.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isUniqueCategory(String Category) {
        return categoryRepository.findByNameIgnoreCase(Category) == null;
    }
}
