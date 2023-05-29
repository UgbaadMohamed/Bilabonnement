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
                " zip_code, customer_phone_number, customer_license_number, customer_age, customer_creditworthy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, c.getCustomer_id(), c.getCustomer_first_name(), c.getCustomer_last_name(),
                c.getCustomer_address(), c.getZip_code(), c.getCustomer_phone_number(),
                c.getCustomer_license_number(), c.getCustomer_age(), c.getCustomer_creditworthy());
    }

    public Boolean deleteCustomer(int customer_id){
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return template.update(sql,customer_id) > 0;
    }





    public void makeCustomerCreditworthy(int customer_id){
        String sql = "UPDATE customer SET customer_creditworthy = 1 WHERE customer_id = ?";
        template.update(sql, customer_id);
    }


    public Customer findCustomerById(int customer_id){
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
       return template.queryForObject(sql, rowMapper, customer_id);
    }

    public Customer findCustomerByLicence(String licence){
        String sql = "SELECT * FROM customer WHERE customer_license_number = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.queryForObject(sql, rowMapper, licence);
    }

}

