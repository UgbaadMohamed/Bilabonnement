package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.repository.CustomerRepo;
import com.example.bilabonnement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepo customerRepo;

    @GetMapping("/")
    public String registerCustomer(@ModelAttribute Customer customer) {
        customerService.createNewUser(customer);
        return "home/customerPage";
    }

    /*
    (@ModelAttribute User user) {
        userService.createNewUser(user);
     */
    @GetMapping("/customerPage")
    public String displayCustomers(Model model) {
        List<Customer> customers = customerService.fetchCustomer();
        model.addAttribute("customers", customers);
        return "home/customerPage";
    }
}
