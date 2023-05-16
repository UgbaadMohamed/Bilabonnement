package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    public void contractInfo(Contract contract){
        contractRepo.contractInfo(contract);
    }

    public List<Contract> viewContract(int contract_id){
        return contractRepo.viewContract(contract_id);
    }



}
