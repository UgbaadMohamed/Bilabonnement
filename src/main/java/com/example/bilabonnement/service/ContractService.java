package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    public void makeContract(Contract contract, int car_id, int customer_id){
        contractRepo.makeContract(contract,car_id, customer_id);
    }

    public List<Contract> viewContracts(int contract_id){
        return contractRepo.viewContracts(contract_id);
    }

    public Contract findContractId(int contract_id){
        return contractRepo.findContractId(contract_id);
    }
    public int totalPriceForMonthlyPayment(int contract_id, Contract contract){

        return contractRepo.totalPriceForMonthlyPayment(contract_id, contract);
    }


    public List<Contract> fetchContracts() {
    return contractRepo.fetchContracts();
    }

    public Contract findContractById(int contract_id){
        return contractRepo.findContractById(contract_id);
    }

    public Contract findContractByCarId(int car_id){
        return contractRepo.findContractByCarId(car_id);
    }

    public Boolean deleteContract(int contract_id){
        return contractRepo.deleteContract(contract_id);
    }
}
