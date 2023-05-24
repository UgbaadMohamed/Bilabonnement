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

    /*

       public void convertPrice(Car car, String currency) {
        if (currency.equals("eu")) {
            car.setCar_price(car.getCar_price() * 0.1343);
        }
    }
     */
    /*

        MyClass obj = new MyClass();

        // Act
        obj.myMethod();

        // Assert
        // Add assertions here to verify the side effects or changes caused by the method
        // For example, you could check the state of an object after calling the method

        // Assert that a specific attribute has been modified
        assertEquals(expectedValue, obj.getAttribute());

        // Assert that a specific method has been called
        assertTrue(obj.isOtherMethodCalled());

        // Assert that a specific condition is met
        assertTrue(condition);
    }
}

     */
}