package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepo;
import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarService {

    @Autowired
    CarRepo carRepo;

    public List<Car> fetchCars(){
        return carRepo.fetchCars();
    }


    public List<Car> viewCars(int car_id){
        return carRepo.viewCars(car_id);
    }
}
