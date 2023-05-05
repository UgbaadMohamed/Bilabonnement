package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class HomeController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public String displayCustomers(Model model) {
        List<Customer> customers = customerService.fetchWishList();
        model.addAttribute("customers", customers);
        return "customer";
    }
}
