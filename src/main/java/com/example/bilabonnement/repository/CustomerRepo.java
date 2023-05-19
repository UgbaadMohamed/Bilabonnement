package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate template;

    public List<Customer> fetchAllCustomer(){
        String sql = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
    public void createCustomer(Customer c) {
        String sql = "INSERT INTO customer (customer_id, customer_first_name, customer_last_name, customer_address," +
                " customer_zip, customer_phone_number, customer_license_number, customer_age, customer_creditworthy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, c.getCustomer_id(), c.getCustomer_first_name(), c.getCustomer_last_name(),
                c.getCustomer_address(), c.getCustomer_zip(), c.getCustomer_phone_number(),
                c.getCustomer_license_number(), c.getCustomer_age(), c.getCustomer_creditworthy());
    }


}

