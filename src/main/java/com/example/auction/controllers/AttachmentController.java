package com.example.auction.controllers;

import com.example.auction.models.Attachment;
import com.example.auction.models.Item;
import com.example.auction.models.User;
import com.example.auction.services.AttachmentService;
import com.example.auction.services.ItemService;
import com.example.auction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Optional;


@Controller
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/attachment")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("attachmentsPage", attachmentService.getAll(pageable));
        return "attachment/list";
    }

    @RequestMapping(path = "/attachment/{username}")
    public String list(Model model, Pageable pageable, @PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        List<Item> items = itemService.getAllByUser(user);
        model.addAttribute("attachmentsPage", attachmentService.getAttachmentsByItems(pageable, items));
        return "attachment/list";
    }

    @RequestMapping(path = {"/attachment/add", "/attachment/edit/{id}"}, method= RequestMethod.GET)
    public String form(Model model, @PathVariable Optional<Long> id) {
        Attachment attachment;
        attachment = getAttachment(model, id);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("attachment", attachment);
        return "attachment/form";
    }

    @RequestMapping(path = {"/attachment/add/{username}", "/attachment/edit/{id}/{username}"}, method= RequestMethod.GET)
    public String form(Model model, @PathVariable Optional<Long> id, @PathVariable("username") String username) {
        Attachment attachment;
        User user = userService.getUserByUsername(username);
        attachment = getAttachment(model, id);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("items", itemService.getAllByUser(user));
        model.addAttribute("attachment", attachment);
        return "attachment/form";
    }

    private Attachment getAttachment(Model model, @PathVariable Optional<Long> id) {
        Attachment attachment;
        if(id.isPresent()){
            attachment = attachmentService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            attachment = new Attachment();
            model.addAttribute("action", "add");
        }
        return attachment;
    }

    @RequestMapping(path = {"/attachment/add","/attachment/add/{username}", "/attachment/edit/{id}/{username}"}, method = RequestMethod.POST)
    public String processForm(Model model, @ModelAttribute("action") String action, @Valid @ModelAttribute("attachment") Attachment attachment, BindingResult errors, @PathVariable("id") Optional<Long> id, @PathVariable("username") Optional<String> username) throws IOException {

        if(attachment.getFile() != null && attachment.getFile().getOriginalFilename().indexOf('.') != -1){
            String suffix = attachment.getFile().getOriginalFilename().substring(attachment.getFile().getOriginalFilename().lastIndexOf("."));
            attachment.setSuffix(suffix);
            attachment.setType(attachment.getFile().getContentType());
            attachment.setContent(attachment.getFile().getBytes());
            attachment.setFile(null);
        }

        if(errors.hasErrors()){
            model.addAttribute("users", userService.getAll());
            return "attachment/form";
        }
        attachmentService.save(attachment);
        return username.map(s -> "redirect:/attachment/" + s).orElse("redirect:/attachment/");
    }

    @RequestMapping(path = {"/attachment/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        Attachment attachment = attachmentService.getById(id);
        model.addAttribute("attachment", attachment);
        return "attachment/details";
    }

    @RequestMapping(path = {"/attachment/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        attachmentService.delete(id);
        return "redirect:/attachment";
    }

    @RequestMapping(path = {"/attachment/download/{id}"})
    public HttpEntity<byte[]> download(@PathVariable Long id) throws Exception {
        Attachment attachment = attachmentService.getById(id);
        HttpHeaders header = new HttpHeaders();
        String [] contentTypes = attachment.getType().split("/");
        header.setContentType(new MediaType(contentTypes[0], contentTypes[1]));
        header.set("Content-Disposition", "inline; filename=" + attachment.getName()+attachment.getSuffix());
        header.setContentLength(attachment.getContent().length);
        return new HttpEntity<byte[]>(attachment.getContent(), header);

    }

}
