package com.example.auction.controllers;

import com.example.auction.models.Role;
import com.example.auction.models.User;
import com.example.auction.repositories.RoleRepository;
import com.example.auction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = {"/user/add", "/user/edit/{id}", "/register"})
    public String registration(Model model, @PathVariable("id") Optional<Long> id) {
        User user;
        if(id.isPresent()){
            user = userService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            user = new User();
            model.addAttribute("action", "Register");
        }
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userCommand", user);
        return "user/registrationForm";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userCommand", new User());
        return "user/loginForm";
    }

    @PostMapping({"/user/add", "/user/edit/{id}", "/register"})
    public String registration(Model model, @ModelAttribute("action") String action, @Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult, @PathVariable("id") Optional<Long> id) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "user/registrationForm";
        }
        userService.save(userForm);
        if(id.isPresent()){
            return "redirect:/user";
        }
        return "user/registrationSuccess";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("enabled");
    }


    @RequestMapping(path = "/user")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("usersPage", userService.getAll(pageable));
        model.addAttribute("roles", Role.Types.values());
        return "user/list";
    }

    @RequestMapping(path = {"/user/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getById(id));
        return "user/details";
    }

    @RequestMapping(path = {"/user/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user";
    }
}