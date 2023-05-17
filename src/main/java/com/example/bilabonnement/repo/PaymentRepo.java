package com.example.bilabonnement.repo;

import com.example.bilabonnement.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepo {
    @Autowired
    private JdbcTemplate template;

    public void finalizeWithPatyment(Payment payment) {
        String sql = "INSERT INTO Payment (payed, card_number, registration_number, contract_id) VALUES (?, ?, ?, ?)";
        template.update(sql, payment.isPayed(), payment.getCard_number(), payment.getRegistration_number(), payment.getContract_id());
    }




}

