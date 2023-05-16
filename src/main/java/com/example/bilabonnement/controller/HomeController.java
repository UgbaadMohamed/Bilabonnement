package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.ContractService;
import com.sun.source.tree.ReturnTree;
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
        return "home/homePage";
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

    @PostMapping("/pickLocation")
    public String pickLocation(@ModelAttribute Car car) {
        carService.location(car.getCar_location(), car.getCar_id());
        return "redirect:/";
    }

    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id, Model model) {
        List<Car> cars =carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        System.out.println(cars);
        return "home/carinformation";
    }


    @GetMapping("/viewContract/{contract_id}")
    public String viewContract(@PathVariable("contract_id") int contract_id,Model model) {
        List<Contract> contracts =contractService.viewContract(contract_id);
        model.addAttribute("contracts", contracts);
        System.out.println(contracts);
        return "home/contract";
    }




    @GetMapping("/contract/{car_id}")
    public String contract(@PathVariable("car_id") int car_id, Model model) {
        model.addAttribute("car", car_id);
        return "home/contract";
    }

    @PostMapping("/contractinfo")
    public String contractinfo(@ModelAttribute Contract contract) {
        System.out.println(contract);
        contractService.contractInfo(contract);
        return "home/homePage";
    }







}
