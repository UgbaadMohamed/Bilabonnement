package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    public void chooseRentingPeriod(int car_id){
        contractRepo.chooseRentingPeriod(car_id);
    }


}
