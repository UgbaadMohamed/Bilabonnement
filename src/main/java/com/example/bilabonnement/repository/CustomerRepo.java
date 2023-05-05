package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo {

    @Autowired
    private JdbcTemplate template;

    public void newCustomer(Customer c) {
        String sql = "INSERT INTO user (customer_id, customer_name, customer_adress, customer_zip, " +
                "customer_phone_number, customer_license_number, customer_driver_since_date, customer_validation ) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, c.getCustomer_id(), c.getCustomer_name(), c.getCustomer_address(), c.getCustomer_zip(),
                c.getCustomer_phone_number(), c.getCustomer_license_number(), c.getCustomer_driver_since_date(), c.getCustomer_validation());
    }












    public boolean validateLogin(String username, String user_password) {
        String sql = "SELECT username, user_password FROM user WHERE username = ? AND user_password = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        try {
            Customer c = template.queryForObject(sql, rowMapper, username, user_password);
            return c != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
/*
customer_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
customer_name VARCHAR(100),
customer_address VARCHAR(100) ,
customer_zip INT,
customer_phone VARCHAR(20) ,
customer_license_number VARCHAR(20),
customer_driver_since_date DATE,
customer_validation TINYINT NOT NULL,
car_id INT,
 */