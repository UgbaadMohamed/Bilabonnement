package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarService {

    @Autowired
    CarRepo carRepo;

    public List<Car> fetchAvailableCars(){
        return carRepo.fetchAvailableCars();
    }
    public List<Car> viewCars(int car_id){
        return carRepo.viewCars(car_id);
    }

     public int totalMonthlyPrice(){
        return carRepo.totalMonthlyPrice();
    }

    public void location(String car_location, int car_id){
        carRepo.location(car_location,car_id);
    }
    public Car findCarById(int id){
        return carRepo.findCarById(id);
    }

       public List<Car> searchSpecificCar(String car_brand){
           return carRepo.searchSpecificCar(car_brand);
       }


    public Car findCarByContractId(int contract_id){
        return carRepo.findCarByContractCarId(contract_id);
    }

    public void convertPrice(Car car, String currency) {
        if (currency.equals("eu")) {
            car.setCar_price(car.getCar_price() * 0.1343);
        }
    }

    public List<Car> fetchCarsInAuction() {
        return carRepo.fetchCarsInAuction();
    }
    public Boolean sellCar(Car car){
        return carRepo.sellCar(car);
    }

}
