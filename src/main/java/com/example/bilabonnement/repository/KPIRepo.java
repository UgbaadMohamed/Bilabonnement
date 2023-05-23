package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.model.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class KPIRepo {
    @Autowired
    private JdbcTemplate template;

    public List<Car> totalRentedCars() {
        String sql = "SELECT car_brand,car_model,car_plate\n" +
                "FROM car c\n" +
                "inner JOIN contract a ON c.car_id = a.car_id\n" +
                "WHERE a.contract_id IS NOT NULL;";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
       List<Car> car = template.query(sql, rowMapper);
       return car;
    }


    public List<Car> totalavailabelCars() {
        String sql = "SELECT c.car_brand, c.car_model, c.car_plate\n" +
                "FROM car c\n" +
                "LEFT JOIN contract a ON c.car_id = a.car_id\n" +
                "WHERE a.contract_id IS NULL;\n";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> cars = template.query(sql, rowMapper);
        return cars;
    }

    public List<Car> orderByRentalEndDate() {
        String sql = "SELECT a.contract_end_date, c.car_brand, c.car_model, c.car_plate\n" +
                "    FROM car c\n" +
                "    LEFT JOIN contract a ON c.car_id = a.car_id\n" +
                "    WHERE a.contract_id IS NOT NULL\n" +
                "    ORDER BY a.contract_end_date ASC;";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> car = template.query(sql, rowMapper);
        return car;
    }

    public List<Contract> findRentalEndDate() {
        String sql = "SELECT a.contract_end_date" +
                "    FROM car c\n" +
                "    LEFT JOIN contract a ON c.car_id = a.car_id\n" +
                "    WHERE a.contract_id IS NOT NULL\n" +
                "    ORDER BY a.contract_end_date ASC;";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        List<Contract> contracts = template.query(sql, rowMapper);
        return contracts;
    }

    public List<Payment> payedNow() {
        String sql = "SELECT * FROM payment\n" +
                "    WHERE payed = 1;";
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
        List<Payment> contracts = template.query(sql, rowMapper);
        return contracts;
    }

    public List<Payment> notPayed() {
        String sql = "SELECT * FROM payment\n" +
                "    WHERE payed = 0;";
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
        List<Payment> contracts = template.query(sql, rowMapper);
        return contracts;
    }






















}
