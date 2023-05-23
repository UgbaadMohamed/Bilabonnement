package com.example.bilabonnement.service;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepo paymentRepo;

    public void finalizeWithPayment(Payment payment, Contract contract) {
        paymentRepo.finalizeWithPayment(payment, contract);
    }


}
