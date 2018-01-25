package com.example.auction.controllers;

import com.example.auction.models.Category;
import com.example.auction.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
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

    @RequestMapping(path = "/category")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("categoriesPage", categoryService.getAll(pageable));
        return "category/list";
    }

    @RequestMapping(path = {"/category/add", "/category/edit/{id}", "/category/suggest"}, method= RequestMethod.GET)
    public String form(Model model, @PathVariable Optional<Long> id) {
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

    @RequestMapping(path = {"/category/add", "/category/edit/{id}", "/category/suggest"}, method = RequestMethod.POST)
    public String processForm(@ModelAttribute("action") String action, @Valid @ModelAttribute("category") Category category, BindingResult errors, Principal principal, @PathVariable("id") Optional<Long> id) {

        if(errors.hasErrors()){
            return "category/form";
        }
        categoryService.save(category);
        if(principal instanceof UserDetails){
            for (Object role:((UserDetails) principal).getAuthorities().toArray()) {
                if(role.equals("ROLE_ADMIN")){
                    return "redirect:/category";
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(path = {"/category/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("category", categoryService.getById(id));
        return "category/details";
    }

    @RequestMapping(path = {"/category/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        model.addAttribute("category", categoryService.getById(id));
        return "redirect:/category";
    }
}
