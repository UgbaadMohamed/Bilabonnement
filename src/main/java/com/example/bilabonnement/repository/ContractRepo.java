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

    public boolean makeContract(Contract contract, int car_id, int customer_id) {
        String sql = "INSERT INTO contract (customer_id, car_id, contract_start_date, contract_end_date," +
                " contract_maximum_km) VALUES (?, ?, ?, ?, ?)";
       return template.update(sql, customer_id, car_id, contract.getContract_start_date(), contract.getContract_end_date(),
                contract.getContract_maximum_km()) >0;

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
                "contract_end_date, contract_maximum_km FROM contract";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }*/


        public Contract findContractById ( int contract_id){
            String sql = "SELECT contract_id, customer_id, car_id, contract_start_date, " +
                    "contract_end_date, contract_maximum_km FROM contract WHERE contract_id = ?";
            RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
            Contract contract = template.queryForObject(sql, rowMapper, contract_id);
            return contract;
        }


    public Contract findContractByCarId (int car_id){
        String sql = "SELECT contract_id, customer_id, car_id, contract_start_date, " +
                "contract_end_date, contract_maximum_km FROM contract WHERE car_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract contract = template.queryForObject(sql, rowMapper, car_id);
        return contract;
    }





    public Boolean deleteContract(int contract_id){
        String sql = "DELETE FROM contract WHERE contract_id = ?";
        return template.update(sql, contract_id) > 0;
    }


    public int totalPriceForMonthlyPayment(int car_id, Contract c){
            String sql2 ="SELECT subscription_price, \n" +
                    "      PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM contract_end_date), EXTRACT(YEAR_MONTH FROM contract_start_date)) + 1 AS selected_months,\n" +
                    "       (PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM contract_end_date), EXTRACT(YEAR_MONTH FROM contract_start_date)) + 1) * subscription_price AS total_price\n" +
                    "FROM contract JOIN car c ON c.car_id= contract_id WHERE car_id = ?";
            int sum= template.queryForObject(sql2, Integer.class, car_id);
            c.setTotalPriceForPayment(sum);
            return c.getTotalPriceForPayment();
        }

    }



