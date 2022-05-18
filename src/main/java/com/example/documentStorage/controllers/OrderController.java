package com.example.documentStorage.controllers;

import com.example.documentStorage.domains.Order;
import com.example.documentStorage.repositories.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Valid
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
//    @GetMapping("/current")
//    public String orderForm(Model model) {
//        model.addAttribute("order", new Order());
//        return "orderForm";
//    }

//    @PostMapping
//    public String processOrder(@Valid Order order, Errors errors) {
//        if (errors.hasErrors()) {
//            log.error("Errors made in ordering are: " +errors);
//            return "orderForm";
//        }
//        log.info("Order submitted: " + order);
//        return "redirect:/";
//    }

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();  //reset the session
        return "redirect:/";
    }
}