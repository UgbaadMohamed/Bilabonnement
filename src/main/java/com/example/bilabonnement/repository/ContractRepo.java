package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractRepo
{
    @Autowired
    private JdbcTemplate template;

    /*public List<Contract> fetchContracts(){
        String sql = "SELECT DISTINCT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }*/

    public Contract findContractById(int contract_id){
        String sql = "SELECT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract contract = template.queryForObject(sql, rowMapper, contract_id);
        return contract;
    }
}
