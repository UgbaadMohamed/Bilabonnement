package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContractService {

    @Autowired
    ContractRepo contractRepo;


    public boolean makeContract(Contract contract, int car_id, int customer_id) {
        // Check if the contract start date is at least 3 months (90 days) from the current date And
        //if end_date is less than 36
        if (ChronoUnit.DAYS.between(contract.getContract_start_date(), contract.getContract_end_date()) >= 120 &&
                ChronoUnit.MONTHS.between(contract.getContract_start_date(), contract.getContract_end_date()) < 36) {

            return contractRepo.makeContract(contract, car_id, customer_id,1);
        } else if (ChronoUnit.DAYS.between(contract.getContract_start_date(), contract.getContract_end_date()) == 150) {
            contractRepo.makeContract(contract, car_id, customer_id,0);
            return contractRepo.makeContract(contract, car_id, customer_id,0);
        }
        return false;

    }

    public List<Contract> viewContracts(int contract_id){
        return contractRepo.viewContracts(contract_id);
    }

    public Contract findContractId(int contract_id){
        return contractRepo.findContractId(contract_id);
    }
    public int totalPriceForMonthlyPayment(int contract_id){


        return contractRepo.totalPriceForMonthlyPayment(contract_id);
    }

    public Map<Contract, Car> mapOfcontractsandcar(){

        List<Contract> fetchContracts = contractRepo.fetchContracts();
        List<Car>cars = contractRepo.carsWithContract();

       // HashMap<Contract, Car> map = new HashMap<>();
        Map<Contract, Car> map = new HashMap<>();

        for (int i = 0; i < fetchContracts.size(); i++) {
            Contract contract = fetchContracts.get(i);
            Car car = cars.get(i);
            map.put(contract,car);
        }
        return map;
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
