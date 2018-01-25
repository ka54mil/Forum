package com.example.auction.validators;

import com.example.auction.services.CategoryService;
import com.example.auction.validators.annotations.UniqueCategory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(UniqueCategory uniqueCategory) {

    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
        return categoryService == null || (category != null && categoryService.isUniqueCategory(category));
    }
}
