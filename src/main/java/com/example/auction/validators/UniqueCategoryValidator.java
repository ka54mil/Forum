package com.example.auction.validators;

import com.example.auction.models.Category;
import com.example.auction.services.CategoryService;
import com.example.auction.validators.annotations.UniqueCategory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, Category> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(UniqueCategory uniqueCategory) {
    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        return categoryService == null || (category != null && category.getName() != null && categoryService.isUniqueCategory(category));
    }
}
