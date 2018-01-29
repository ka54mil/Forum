package com.example.auction.controllers;

import com.example.auction.controllers.helpers.SearchFilter;
import com.example.auction.models.Category;
import com.example.auction.models.Item;
import com.example.auction.models.User;
import com.example.auction.services.AttachmentService;
import com.example.auction.services.CategoryService;
import com.example.auction.services.ItemService;
import com.example.auction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttachmentService attachmentService;

    @ModelAttribute("searchCommand")
    public SearchFilter getSimpleSearch(){
        return new SearchFilter();
    }

    @RequestMapping(path = "/")
    public String index(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") SearchFilter search) {
        model.addAttribute("categoriesList", categoryService.getAllActive());
        List<Item> items = itemService.getAllOnSale(search, Item.Statuses.Aktywna, new Date());
        if(items == null){
            items = new ArrayList<>();
        }
        if(search.getCategories()!= null && !search.getCategories().isEmpty() && !items.isEmpty()){
            List<Item> result = new ArrayList<>();
            for (Item item : items){
                boolean exist = false;
                for(Category category : search.getCategories()){
                    if(item.getCategories().contains(categoryService.getById(category.getId()))){
                        exist = true;
                        break;
                    }
                }
                if(exist){
                    result.add(item);
                }
            }
            items = result;
        }

        model.addAttribute("itemsPage", new PageImpl(items, pageable, items.size()));
        return "home";
    }

    @RequestMapping(path = "/item")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("itemsPage", itemService.getAll(pageable));
        return "item/list";
    }

    @RequestMapping(path = "/item/{username}")
    public String list(Model model, Pageable pageable, @PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("itemsPage", itemService.getAllByUser(pageable, user));
        return "item/list";
    }

    @GetMapping({"/item/add", "/item/edit/{id}"})
    public String form(Model model, @PathVariable("id") Optional<Long> id) {
        Item item;
        if(id.isPresent()){
            item = itemService.getById(id.get());
            model.addAttribute("action", "edit");
        } else {
            item = new Item();
            model.addAttribute("action", "add");
        }
        model.addAttribute("item", item);
        model.addAttribute("statuses", Item.Statuses.values());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("categories", categoryService.getAllActive());

        return "item/form";
    }

    @PostMapping({"/item/add", "/item/edit/{id}"})
    public String processForm(Model model, @ModelAttribute("action") String action, @Valid @ModelAttribute("item") Item item, BindingResult errors, @PathVariable("id") Optional<Long> id) {

        if(errors.hasErrors()){
            model.addAttribute("statuses", Item.Statuses.values());
            model.addAttribute("users", userService.getAll());
            model.addAttribute("categories", categoryService.getAll());

            return "item/form";
        }

        itemService.save(item);
        return "redirect:/item";
    }

    @RequestMapping(path = {"/item/details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        model.addAttribute("attachments", attachmentService.getAttachmentsByItem(item));

        return "item/details";
    }

    @RequestMapping(path = {"/item/delete/{id}"})
    public String delete(Model model, @PathVariable Long id) {
        itemService.delete(id);
        return "redirect:/item";
    }

    @PostMapping(path = {"/bid/{id}/{username}"})
    public String bid(@PathVariable Long id, @PathVariable("username") String username, @RequestParam BigDecimal price) {
        User user = userService.getUserByUsername(username);
        if(user != null){
            Item item = itemService.getById(id);
            if(item != null){
                item.setPrice(price);
                item.setWinner(user);
                itemService.save(item);
            }
        }
        return "redirect:/item/details/"+id;
    }
}
