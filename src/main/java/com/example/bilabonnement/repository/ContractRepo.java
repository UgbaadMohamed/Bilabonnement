package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ContractRepo {

    @Autowired
    JdbcTemplate template;

    public void chooseRentingPeriod(int car_id) {
        String sql = "INSERT INTO rental_contract(start_date, end_date,contract_maximum_km,contract_start_km) Values ?,?,?,? WHERE car_id =?";
        template.update(sql,car_id);
    }










}
