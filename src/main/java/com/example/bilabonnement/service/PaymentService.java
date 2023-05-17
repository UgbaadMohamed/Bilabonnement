package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.repo.KPIRepo;
import com.example.bilabonnement.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepo paymentRepo;

    public void finalizeWithPatyment(Payment payment) {
        paymentRepo.finalizeWithPatyment(payment);
    }


}
