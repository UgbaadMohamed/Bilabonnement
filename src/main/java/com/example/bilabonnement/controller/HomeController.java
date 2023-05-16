package com.example.bilabonnement.controller;
import com.example.bilabonnement.model.*;
import com.example.bilabonnement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;


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
    @Autowired
    ReviewService reviewService;

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
        return "home/findReviewTarget";
    }

    @GetMapping("/review")
    public String review(@RequestParam("contract_id") int contract_id, Model model){
        if (reviewService.checkIfReviewed(contract_id)){
            return "home/reviewDenied";
        } else {

            Contract contract = contractService.findContractById(contract_id);
            model.addAttribute("contract", contract);
            return "home/review";
        }
    }

    @GetMapping("/reviewSubmitted")
    public String reviewSubmitted(Review review, Model model) {
        reviewService.addReview(review);
        if (review.getBuying_customer() == 1){
            Car car = carService.findCarByContractId(review.getContract_id());
            model.addAttribute("car", car);
            return "home/carSale";
        } else
        return "home/frontPage";
    }

    /*@PostMapping("/priceConverter")
    public String C2F(WebRequest webRequest, @ModelAttribute Calculator temp, Model model) {
        String btn = webRequest.getParameter("type");
        if (btn.equals("C2F")) {
            temp.setFahrenheit(temp.getTemperature() * 9 / 5 + 32);
            model.addAttribute("res", temp.getFahrenheit());

        } else if (btn.equals("F2C")){
            temp.setCelsius(temp.getTemperature()- 32 * 5/9);
            model.addAttribute("res", temp.getCelsius());
        }
        return "home/index";
    }*/

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
    /*@GetMapping ("/creditValidation")
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



    @PostMapping("/saveConditionReport")
    public String saveConditionReport(@ModelAttribute ConditionReport conditionReport){
        conditionReportService.saveConditionReport(conditionReport);
        return "home/frontPage";
    }



     */

}

