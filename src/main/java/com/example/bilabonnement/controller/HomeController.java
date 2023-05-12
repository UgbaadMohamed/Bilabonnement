package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class HomeController {

        @Autowired
        CustomerService customerService;

        @GetMapping("/")
        public String customerForm(Model model) {
            List<Customer> customerList = customerService.fetchAllCustomer();
            model.addAttribute("customers", customerList);
            return "home/customerForm";
        }
        @GetMapping("/customerPage")
        public String customerPage(){
            return "home/customerPage";
        }

        @PostMapping("/createCustomer")
        public String createCustomer(@ModelAttribute Customer customer) {
            customerService.createCustomer(customer);
            return "home/customerPage";
        }


    }



