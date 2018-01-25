package com.example.auction.controllers;

import com.example.auction.models.Category;
import com.example.auction.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path = "/*")
    public String index(Model model, Pageable pageable) {
        model.addAttribute("categoriesPage", categoryService.getAllActive(pageable));
        return "home";
    }

    @RequestMapping(path = "/admin/category")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("categoriesPage", categoryService.getAll(pageable));
        return "category/list";
    }

    @RequestMapping(path = {"/admin/category/add", "/admin/category/edit", "/category/suggest"}, method= RequestMethod.GET)
    public String form(Model model, Optional<Long> id) {
        Category category;
        if(id.isPresent()){
            category = categoryService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            category = new Category();
            model.addAttribute("action", "add");
        }
        model.addAttribute("category", category);
        return "category/form";
    }

    @RequestMapping(path = {"/admin/category/add", "/admin/category/edit", "/category/suggest"}, method = RequestMethod.POST)
    public String processForm(@ModelAttribute("action") String action, @Valid @ModelAttribute("category") Category category, BindingResult errors) {

        if(errors.hasErrors()){
            return "category/form";
        }
        categoryService.save(category);
        return "redirect:/category";
    }

    @RequestMapping(path = {"/admin/category/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("category", categoryService.getById(id));
        return "category/details";
    }

    @RequestMapping(path = {"/admin/category/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        model.addAttribute("category", categoryService.getById(id));
        return "redirect:/admin/category";
    }
}
