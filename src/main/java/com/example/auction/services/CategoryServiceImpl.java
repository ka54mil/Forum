package com.example.auction.services;

import com.example.auction.models.Category;
import com.example.auction.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isUniqueCategory(String Category) {
        return categoryRepository.findByNameIgnoreCase(Category) == null;
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public Page<Category> getAllActive(Pageable pageable) {
        return categoryRepository.findAllByActive(true, pageable);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findOne(id);
    }
}
