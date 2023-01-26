package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final MemberService memberService;

    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        model.addAttribute("members", memberService.findMembers());
        model.addAttribute("items", itemService.findItems());
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId")Long itemId,
                        @RequestParam("quantity")int quantity){
        orderService.order(memberId, itemId, quantity);
        return "redirect:/orders/";
    }
}
