package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.dto.BookForm;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping
    public String create(BookForm form){
        itemService.saveItem(Book.createBook(form));
        return "redirect:/";
    }
}
