package com.example.bilabonnement.controller;
import com.example.bilabonnement.model.ConditionReport;
import com.example.bilabonnement.model.StaffMember;
import com.example.bilabonnement.service.ConditionReportService;
import com.example.bilabonnement.service.ContractService;
import com.example.bilabonnement.service.StaffMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.service.CarService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CarService carService;
    @Autowired
    StaffMemberService staffService;
    @Autowired
    ConditionReportService conditionReportService;
    @Autowired
    ContractService contractService;

    int car_id;
    @GetMapping("//")
    public String homePage(Model model) {
        List<Car> cars = carService.fetchCars();
        model.addAttribute("cars", cars);
        return "home/homePage";
    }

    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id, Model model) {
        List<Car> cars =carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        System.out.println(cars);
        return "home/carinformation";
    }


    @GetMapping("/availability/{car_id}")
    public String addItem(@PathVariable("car_id") int car_id, Model model) {
        model.addAttribute("car_id", car_id);
        return "home/carinformation";
    }

    @PostMapping("/availabilityCar")
    public String addItemToWishlist(@ModelAttribute Car car) {
        carService.chooseRentingPeriod(car, car.getStart_date(), car.getEnd_date());
        return "redirect:/homePage";
    }
    private int staff_id;

    @GetMapping("/")
    public String frontPage(){
        return "home/conditionReportDocumentation";
    }

    @PostMapping("/login")
    public String login(@RequestParam("staff_member_username") String staff_member_username,
                        @RequestParam("staff_member_password")
                        String staff_member_password, Model model) {
        //model.addAttribute("staff_member", staffService.validateLogin(staff_member_username, staff_member_password));

        if (staffService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffService.getStaffMember(staff_member_username, staff_member_password);
            model.addAttribute("staff_member", staffMember);
            return "home/stafftype";
        }
     else {
        model.addAttribute("error", "Forkert username eller password.");
        return "home/frontPage";
    }
    }
    @GetMapping ("/creditValidation")
    public String creditValidation() {
            return "home/creditValidation";
    }

    @PostMapping("/receivedDocuments")
    public String receivedDocuments(@RequestParam("q1") String q1,
                              @RequestParam("q2") String q2) {
        if (q1.equals("ja") && q2.equals("ja")) {
            return "redirect:/creditValidation";
        } else {
            return "home/creditDocumentation";
        }
    }
    @PostMapping("/creditvalidation-form")
    public String creditvalidationForm() {
        return "home/contract";
    }

    @PostMapping("/conditionReport")
    public String conditionReport(@RequestParam("contract_id") int contract_id, Model model) {
        model.addAttribute("contract", contractService.findContractById(contract_id));
        return "home/conditionReport";
    }

    /*@GetMapping("/sendConditionReport/{contract_id}")
    public String sendConditionReport(@PathVariable("contract_id") int contract_id, Model model) {
        model.addAttribute("contract_id", contract_id);
        return "home/sendConditionReport";
    }*/

    @PostMapping("/saveConditionReport")
    public String saveConditionReport(@ModelAttribute ConditionReport conditionReport){
        conditionReportService.saveConditionReport(conditionReport);
        return "home/frontPage";
    }

    /*@PostMapping("/sendConditionReport")
    public String sendConditionReport() {
        return "home/sendConditionReport";
    } <input type="hidden" name="contract_id" th:value="${contract_id}">*/

}

