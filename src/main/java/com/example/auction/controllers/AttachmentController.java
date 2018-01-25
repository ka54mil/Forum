package com.example.auction.controllers;

import com.example.auction.models.Attachment;
import com.example.auction.services.AttachmentService;
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
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(path = "/attachment")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("categoriesPage", attachmentService.getAll(pageable));
        return "attachment/list";
    }

    @RequestMapping(path = {"/attachment/add", "/attachment/edit"}, method= RequestMethod.GET)
    public String form(Model model, Optional<Long> id) {
        Attachment attachment;
        if(id.isPresent()){
            attachment = attachmentService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            attachment = new Attachment();
            model.addAttribute("action", "add");
        }
        model.addAttribute("attachment", attachment);
        return "attachment/form";
    }

    @RequestMapping(path = {"/attachment/add", "/attachment/edit"}, method = RequestMethod.POST)
    public String processForm(@ModelAttribute("action") String action, @Valid @ModelAttribute("attachment") Attachment attachment, BindingResult errors) {

        if(errors.hasErrors()){
            return "attachment/form";
        }
        attachmentService.save(attachment);
        return "redirect:/attachment";
    }

    @RequestMapping(path = {"/attachment/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("attachment", attachmentService.getById(id));
        return "attachment/details";
    }

    @RequestMapping(path = {"/attachment/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        model.addAttribute("attachment", attachmentService.getById(id));
        return "redirect:/attachment";
    }
}
