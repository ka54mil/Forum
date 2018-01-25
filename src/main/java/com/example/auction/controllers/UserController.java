package com.example.auction.controllers;

import com.example.auction.models.Attachment;
import com.example.auction.models.Role;
import com.example.auction.models.User;
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

    @GetMapping({"/user/add", "/user/edit", "/register"})
    public String registration(Model model, Optional<Long> id) {
        User user;
        if(id.isPresent()){
            user = userService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            user = new User();
            model.addAttribute("action", "add");
        }
        model.addAttribute("userCommand", user);
        return "user/registrationForm";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userCommand", new User());
        return "user/loginForm";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("action") String action, @Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/registrationForm";
        }
        userService.save(userForm);
        return "user/registrationSuccess";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("enabled", "roles");
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
        model.addAttribute("user", userService.getById(id));
        return "redirect:/user";
    }
}