package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    CarService carService;
    @GetMapping("/")
    public String homePage(Model model) {
        List<Car> cars = carService.fetchCars();
        model.addAttribute("cars", cars);
        return "home/homePage";
    }

    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id,Model model) {
        List<Car> cars =carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        System.out.println(cars);
        return "home/hompage";
    }



}
