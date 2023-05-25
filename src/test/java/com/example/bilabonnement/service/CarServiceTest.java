package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    @Test
    public void testConvertPrice(){
        CarService carService = new CarService();
        Car car = new Car();
        car.setCar_price(500);
        carService.convertPrice(car, "eu");

        assertEquals(67.15, car.getCar_price());

    }


}