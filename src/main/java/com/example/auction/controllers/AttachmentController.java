package com.example.auction.controllers;

import com.example.auction.models.Attachment;
import com.example.auction.services.AttachmentService;
import com.example.auction.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Optional;

import static com.example.auction.config.WebMvcConfig.rootDir;

@Controller
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(path = "/attachment")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("attachmentsPage", attachmentService.getAll(pageable));
        return "attachment/list";
    }

    @RequestMapping(path = {"/attachment/add", "/attachment/edit/{id}"}, method= RequestMethod.GET)
    public String form(Model model, @PathVariable Optional<Long> id) {
        Attachment attachment;
        if(id.isPresent()){
            attachment = attachmentService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            attachment = new Attachment();
            model.addAttribute("action", "add");
        }
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("attachment", attachment);
        return "attachment/form";
    }

    @RequestMapping(path = {"/attachment/add", "/attachment/edit/{id}"}, method = RequestMethod.POST)
    public String processForm(@ModelAttribute("action") String action, @Valid @ModelAttribute("attachment") Attachment attachment, BindingResult errors, @PathVariable("id") Optional<Long> id) throws IOException {

        if(attachment.getFile() != null){
            int i = 0;

            String suffix = attachment.getFile().getOriginalFilename().substring(attachment.getFile().getOriginalFilename().lastIndexOf("."));
            String fileDirPath = rootDir+attachment.getName()+suffix;
            while(attachmentService.isAttachmentExist(fileDirPath)){
                fileDirPath = rootDir+attachment.getName()+'_'+(i++)+suffix;
            }

            File file = new File(fileDirPath);
            attachment.getFile().transferTo(file);
            attachment.setType(attachment.getFile().getContentType());
            attachment.setPath(fileDirPath);
            attachment.setFile(null);
        }

        if(errors.hasErrors()){
            return "attachment/form";
        }
        attachmentService.save(attachment);
        return "redirect:/attachment";
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
    public Resource download(HttpServletResponse response, @PathVariable Long id) throws IOException {
        Attachment attachment = attachmentService.getById(id);
        String filePath = attachment.getPath();
        File file = new File(filePath);

        if (!file.exists()){
            throw new FileNotFoundException("file with path: " + filePath + " was not found.");
        }
        String suffix = filePath.substring(filePath.lastIndexOf('.'), filePath.length());
        response.setContentType(attachment.getType());
        response.setHeader("Content-Disposition", "inline; filename=" + attachment.getName()+suffix);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        return new FileSystemResource(file);

    }

}
