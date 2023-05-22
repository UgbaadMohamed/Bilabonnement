package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
    public class CustomerService {
        @Autowired
        CustomerRepo customerRepo;


    public List<Customer>fetchAllCustomer(){
        return customerRepo.fetchAllCustomer();
    }

    public void createCustomer(Customer c){
        customerRepo.createCustomer(c);
    }

    public void makeCustomerCreditworthy(int customer_id){
        customerRepo.makeCustomerCreditworthy(customer_id);
    }
    public Customer findCustomerById(int customer_id){
        return customerRepo.findCustomerById(customer_id);
    }

   }

