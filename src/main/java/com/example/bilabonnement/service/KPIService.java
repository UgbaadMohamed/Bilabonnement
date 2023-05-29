package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;

import com.example.bilabonnement.repository.KPIRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KPIService {

    @Autowired
    KPIRepo kpiRepo;

    public List<Car> totalRentedCars() {
        return kpiRepo.totalRentedCars();
    }

    public List<Car> totalavailabelCars() {
        return kpiRepo.totalavailabelCars();
    }




    public List<Car> orderByRentalEndDate() {
        return kpiRepo.orderByRentalEndDate();
    }


    public List<Payment> payedNow() {
        return kpiRepo.payedNow();
    }
    public List<Contract> findRentalEndDate() {
        return kpiRepo.findRentalEndDate();
    }




    public Map<Contract, Car> mapOfcontractsandcar(){

        List<Contract> findRentalEndDate = findRentalEndDate();
        List<Car>cars = orderByRentalEndDate();

        Map<Contract, Car> map = new HashMap<>();

        for (int i = 0; i < findRentalEndDate.size(); i++) {
            Contract contract = findRentalEndDate.get(i);
            Car car = cars.get(i);
            map.put(contract,car);
        }
        return map;
    }


    public List<Payment> notPayed() {
        return kpiRepo.notPayed();
    }
}

