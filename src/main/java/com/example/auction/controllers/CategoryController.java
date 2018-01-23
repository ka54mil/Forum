package com.example.auction.controllers;

import com.example.auction.models.Category;
import com.example.auction.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path = "/category/*")
    public String index(Model model, Pageable pageable) {
        model.addAttribute("CategoriesPage", categoryService.getAllActive(pageable));
        return "home";
    }

    @Secured("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = {"/category/add", "/category/edit"}, method= RequestMethod.GET)
    public String form(Model model, Optional<Long> id) {
        Category category;
        if(id.isPresent()){
            category = categoryService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            category = new Category();
            model.addAttribute("action", "add");
        }
        model.addAttribute("Category", category);
        return "category/form";
    }

    @RequestMapping(path = "/category/suggest")
    public String suggest(Model model) {
        model.addAttribute("Category", new Category());
        model.addAttribute("action", "suggest");
        return "category/form";
    }
}
