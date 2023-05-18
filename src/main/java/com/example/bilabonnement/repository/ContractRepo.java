package com.example.bilabonnement.repository;
import com.example.bilabonnement.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ContractRepo {

    @Autowired
    JdbcTemplate template;

    public void contractInfo(Contract contract, int car_id) {
        String sql = "INSERT INTO contract (contract_start_date, contract_end_date, contract_maximum_km, contract_start_km, car_id) Values (?, ?, ?, ?, ?)";
        template.update(sql,contract.getContract_start_date(), contract.getContract_end_date(), contract.getContract_maximum_km(), contract.getContract_start_km(),car_id);
    }

    public List<Contract> viewLeasedCars(int contract_id){
        String sql= "SELECT car_model, car_brand, image, car_vin FROM contract WHERE contract_id=";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper, contract_id);
    }


    public Contract findContractId(int contract_id){
        String sql="SELECT * FROM contract WHERE contract_id = ?";
        RowMapper<Contract> rowMapper= new BeanPropertyRowMapper<>(Contract.class);
        Contract contract= template.queryForObject(sql, rowMapper, contract_id);
        return contract;
    }

    public Contract findContractById(int contract_id){
        String sql = "SELECT contract_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract contract = template.queryForObject(sql, rowMapper, contract_id);
        return contract;
    }



    }
