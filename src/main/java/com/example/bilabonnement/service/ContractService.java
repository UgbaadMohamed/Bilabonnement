package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    public void contractInfo(Contract contract, int car_id){
        contractRepo.contractInfo(contract,car_id);
    }

    public List<Contract> viewLeasedCars(int contract_id){
        return contractRepo.viewLeasedCars(contract_id);
    }

    public Contract findContractId(int contract_id){
        return contractRepo.findContractId(contract_id);
    }
    public Contract findContractById(int contract_id){
        return contractRepo.findContractId(contract_id);
    }


}
