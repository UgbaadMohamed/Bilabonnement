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


    public void location(String car_location, int car_id){
        carRepo.location( car_location, car_id);
}

    public Car findPersonById(int id){
        return carRepo.findPersonById(id);
    }
       /* public void location(Car car){
            carRepo.location(car.getCar_id(),car.getCar_location());
    }*/
}
