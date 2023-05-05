package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


    @Service
    public class CustomerService {
        @Autowired
        CustomerRepo customerRepo;

        public void createNewUser(Customer c) {
            customerRepo.newCustomer(c);
        }

    }
