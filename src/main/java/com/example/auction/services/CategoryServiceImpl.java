package com.example.auction.services;

import com.example.auction.models.Category;
import com.example.auction.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isUniqueCategory(Category category) {
        if(category.getId() == null){
            return categoryRepository.findByNameIgnoreCase(category.getName()) == null;
        }
        return categoryRepository.findByNameIgnoreCaseAndIdNot(category.getName(), category.getId()) == null;
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
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
    public List<Category> getAllActive() {
        return categoryRepository.findAllByActive(true);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findOne(id);
    }
}
