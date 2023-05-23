package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepo {
    @Autowired
    private JdbcTemplate template;

    public void finalizeWithPayment(Payment payment, Contract contract) {
        String sql = "INSERT INTO Payment (payed, card_number, registration_number, contract_id) VALUES (?, ?, ?, ?)";
        template.update(sql, payment.isPayed(), payment.getCard_number(), payment.getRegistration_number(),
                contract.getContract_id());
    }




}

