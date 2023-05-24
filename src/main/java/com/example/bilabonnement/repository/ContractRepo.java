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

    public void makeContract(Contract contract, int car_id, int customer_id) {
        String sql = "INSERT INTO contract (customer_id, car_id, contract_start_date, contract_end_date," +
                " contract_maximum_km, contract_start_km) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, customer_id, car_id, contract.getContract_start_date(), contract.getContract_end_date(),
                contract.getContract_maximum_km(), contract.getContract_start_km());
    }

    public List<Contract> viewContracts(int contract_id){
        String sql= "SELECT car_model, car_brand, image, car_vin,contract_start_date, contract_end_date FROM car c JOIN contract t ON c.car_id=t.car_id";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper, contract_id);
    }


    public Contract findContractId(int contract_id){
        String sql="SELECT * FROM contract WHERE contract_id = ?";
        RowMapper<Contract> rowMapper= new BeanPropertyRowMapper<>(Contract.class);
        Contract contract= template.queryForObject(sql, rowMapper, contract_id);
        return contract;
    }
   public List<Contract> fetchContracts(){
        String sql = "SELECT contract_id, car_model, car_brand, image, car_vin,contract_start_date, contract_end_date FROM car c JOIN contract t ON c.car_id=t.car_id";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }


   /* public List<Contract> fetchContracts(){
        String sql = "SELECT DISTINCT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }*/


    public Contract findContractById ( int contract_id){
        String sql = "SELECT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract contract = template.queryForObject(sql, rowMapper, contract_id);
        return contract;
    }
    public Contract findContractByCarId (int car_id){
        String sql = "SELECT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km, contract_start_km FROM contract WHERE car_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract contract = template.queryForObject(sql, rowMapper, car_id);
        return contract;
    }


    public int totalPriceForMonthlyPayment(int contract_id, Contract contract){
        String sql2 = "SELECT (PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM t.contract_end_date), EXTRACT(YEAR_MONTH FROM t.contract_start_date)) + 1) * c.subscription_price AS total_price\n" +
                "FROM contract t\n" +
                "JOIN car c ON c.car_id = t.car_id\n" +
                "WHERE t.contract_id = ?";
        int sum= template.queryForObject(sql2, Integer.class, contract_id);
        contract.setTotalPriceForPayment(sum);
        return contract.getTotalPriceForPayment();
    }


    public Boolean deleteContract(int contract_id){
        String sql = "DELETE FROM contract WHERE contract_id = ?";
        return template.update(sql, contract_id) > 0;
    }



    }



