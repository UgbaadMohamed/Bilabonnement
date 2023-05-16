package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ContractRepo {

    @Autowired
    JdbcTemplate template;

    public void contractInfo(Contract contract) {
        String sql = "INSERT INTO contract (contract_id, contract_start_date, contract_end_date, contract_maximum_km, contract_start_km) Values (?, ?, ?, ?, ?)";
        template.update(sql,contract.getContract_id(), contract.getContract_start_date(), contract.getContract_end_date(), contract.getContract_maximum_km(), contract.getContract_start_km());
    }



    public List<Contract> viewContract(int contract_id){
        String sql= "SELECT * FROM contract WHERE contract_id=";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper, contract_id);
    }










}
