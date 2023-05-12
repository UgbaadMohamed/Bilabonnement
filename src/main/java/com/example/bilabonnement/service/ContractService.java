package com.example.bilabonnement.service;

import com.example.bilabonnement.model.ConditionReport;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService
{
    @Autowired
    ContractRepo contractRepo;

    public List<Contract> fetchContracts(){
        return contractRepo.fetchContracts();
    }

    public Contract findContractById(int contract_id){
        return contractRepo.findContractById(contract_id);
    }
}
