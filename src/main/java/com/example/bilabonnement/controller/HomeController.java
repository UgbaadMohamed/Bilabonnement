package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.ContractService;
import com.sun.source.tree.ReturnTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    CarService carService;

    @Autowired
    ContractService contractService;

    int car_id;

    @GetMapping("/homepage")
    public String homePage(Model model) {
        List<Car> cars = carService.fetchCars();
        model.addAttribute("cars", cars);
        return "home/homepage";
    }


    @GetMapping("/createList")
    public String createList() {
        return "home/createList";
    }

    @GetMapping("/carinformation/{car_id}")
    public String carinformation(@PathVariable("car_id") int car_id, Model model){
        model.addAttribute("car", carService.findPersonById(car_id));
        return "home/carinformation";
    }

    @PostMapping("/pickLocation/{car_id}")
    public String pickLocation(@ModelAttribute Car car,@PathVariable("car_id") int car_id) {
        carService.location(car.getCar_location(), car_id);
        return "home/homepage";
    }

    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id, Model model) {
        List<Car> cars =carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        System.out.println(cars);
        return "home/carinformation";
    }


   @GetMapping("/viewLeasedCars/{contract_id}")
    public String viewContract(@PathVariable("contract_id") int contract_id,Model model) {
        List<Contract> contracts =contractService.viewLeasedCars(contract_id);
        model.addAttribute("contracts", contracts);
        System.out.println(contracts);
        return "home/contract";
    }


    @GetMapping("/contract/{car_id}")
    public String contract(@PathVariable("car_id") int car_id, Model model) {
        model.addAttribute("car_id", car_id);
        return "home/contract";
    }

    @PostMapping("/contractinfo/{car_id}")
    public String contractinfo(@ModelAttribute Contract contract,@PathVariable("car_id") int car_id) {
        System.out.println(contract);
        contractService.contractInfo(contract,car_id);
        return "home/contract";
    }

    @GetMapping("/search")
    public String searchForCar(@RequestParam("car_brand") String car_brand, Model model) {
        List<Car> cars= carService.searchSpecificCar(car_brand);
        System.out.println(cars);
        model.addAttribute("cars", cars);
        return "home/search";
    }


   @GetMapping("/stats")
    public String totalPayment(Model model) {
     int total= carService.totalMonthlyPrice();
        model.addAttribute("subscriptionPrice", total);
        return "home/stats";
    }


    @GetMapping("/totalPriceForMonthlyPayment")
    public String totalPayment(@PathVariable("car_id") int car_id,Contract con, Model model) {
       int sum = contractService.totalPriceForMonthlyPayment(car_id, con) ;
        model.addAttribute("con", sum);
        return "home/contract";
    }



}
