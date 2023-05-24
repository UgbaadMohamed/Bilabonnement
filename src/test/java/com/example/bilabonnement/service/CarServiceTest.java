package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    @Test
    public void totalMonthlyPrice() {
        CarService carService = new CarService();
        Car car = new Car();
        car.setSubscription_price(5);
        carService.totalMonthlyPrice();
        assertEquals(5, car.getSubscription_price());

    }
    }