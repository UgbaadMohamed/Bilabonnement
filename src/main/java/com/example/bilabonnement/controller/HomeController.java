package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    CarService carService;
    ContractService contractService;
    int car_id;
    @GetMapping("/")
    public String homePage(Model model) {
        List<Car> cars = carService.fetchCars();
        model.addAttribute("cars", cars);
        System.out.println(cars.get(0).getImage());
        return "home/homePage";
    }


    @PostMapping("/makeList")
    public String makeList(@ModelAttribute Car Car) {
        contractService.chooseRentingPeriod(car_id);
        return "redirect:/homePage";
    }

    @GetMapping("/createList")
    public String createList(){
        return "home/createList";
    }




    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id,Model model) {
        List<Car> cars =carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        System.out.println(cars);
        return "home/carinformation";
    }



    @GetMapping("/availability/{contract_id}")
    public String availability(@PathVariable("contract_id") int contract_id, Model model) {
        model.addAttribute("contract_id", contract_id);
        return "home/carinformation";
    }

    @PostMapping("/availabilityCar")
    public String availabilityCar(@ModelAttribute Contract contract) {
        //contractService.chooseRentingPeriod(contract, contract.getContract_start_date(), contract.getContract_end_date(), contract.getContract_maximum_km(), contract.getContract_start_km());
        return "redirect:/homePage";
    }







}
