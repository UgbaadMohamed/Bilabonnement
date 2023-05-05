package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CarRepo {

@Autowired
JdbcTemplate template;

    public List<Car> fetchCars(){
        String sql = "SELECT * From car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    public List<Car> viewCars(int car_id) {
        String sql = "SELECT * From car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_id);
    }






}
