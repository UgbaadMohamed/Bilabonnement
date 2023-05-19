package com.example.bilabonnement.controller;
import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.model.StaffMember;
import com.example.bilabonnement.service.KPIService;
import com.example.bilabonnement.service.PaymentService;
import com.example.bilabonnement.service.StaffMemberService;
import com.example.bilabonnement.model.*;
import com.example.bilabonnement.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CarService carService;
    @Autowired
    StaffMemberService staffMemberService;

    @Autowired
    KPIService kpiService;
    @Autowired
    PaymentService paymentService;


    private int staff_id;

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
        return "carInformation";
    }


    @GetMapping("/availability/{car_id}")
    public String addItem(@PathVariable("car_id") int car_id, Model model) {
        model.addAttribute("car_id", car_id);
        return "carInformation";
    }

    @PostMapping("/availabilityCar")
    public String addItemToWishlist(@ModelAttribute Car car) {
        carService.chooseRentingPeriod(car, car.getStart_date(), car.getEnd_date());
        return "redirect:/homePage";
    }
    //private int staff_id;

    @GetMapping("/")
    public String frontPage() {
        return "home/frontPage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "home/loginPage";


    @GetMapping("/findReviewTarget")
    public String findReviewTarget() {
            return "home/findReviewTarget";
    }

    @GetMapping("/review")
    public String review(@RequestParam("contract_id") int contract_id, Model model) {
        try {
            if (reviewService.checkIfAlreadyReviewed(contract_id)) {
                return "home/reviewDenied";
            }
            else {
                Contract contract = contractService.findContractById(contract_id);
                model.addAttribute("contract", contract);
                //contract id kommer med til review, til senere brug
                return "home/review";
            }
        } catch (EmptyResultDataAccessException e) { //inputvalidering, hvis kontrakten ikke findes
                return "home/reviewDenied";
        }
    }

    @PostMapping("/reviewSubmitted")
    public String submitReview(Review review, Model model) {
        reviewService.addReview(review);
        //deklaration
        List<Car> carsInAuction;

        /* contract id er blevet overført som en hidden value, og kan nu bruges til at finde den
            pågældende bil (gennem en join) */
        Car car = carService.findCarByContractId(review.getContract_id());
        model.addAttribute("car", car);

        if (review.getBuying_customer() == 1)
            return "home/carSale";
        else
            carsInAuction = carService.fetchCarsInAuction();
            model.addAttribute("cars_in_auction", carsInAuction);
            return "home/auction";
    }

    @GetMapping("/auction")
    public String auction(Model model) {
        List<Car> carsInAuction = carService.fetchCarsInAuction();
        model.addAttribute("cars_in_auction", carsInAuction);
        return "home/auction";
    }

    @PostMapping("/priceConverter")
    public String convertPrice(Car car, Model model, @RequestParam("currency") String currency, Review review,
                               Contract contract) {
        car = carService.findCarByContractId(car.getCar_id());
        model.addAttribute("car", car);
        carService.convertPrice(car, currency);

        // Retrieve review and contract objects from the model
        review = (Review) model.getAttribute("review");
        contract = (Contract) model.getAttribute("contract");

        model.addAttribute("review", review);
        model.addAttribute("contract", contract);

        return "home/carSale";
    }

    @PostMapping("/sellCar")
    public String sellCar(Car car) {
        carService.sellCar(car);

        return "home/conditionReportDocumentation";
    }


    @PostMapping("/loginPage")
    public String loginPage(@RequestParam("staff_member_username") String staff_member_username,
                            @RequestParam("staff_member_password")
                            String staff_member_password, Model model) {
        if (staffMemberService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffMemberService.findStaffMember(staff_member_username,
                    staff_member_password);
            model.addAttribute("staff_member", staffMember);
            return "home/staffType";
        }
        return "home/loginPage";
    }

    @GetMapping("/creditValidation")

    @GetMapping("/creditDocumentation")
    public String creditDocumentation() {
        return "home/creditDocumentation";
    }

    @GetMapping ("/creditValidation")
    public String creditValidation() {
        return "home/creditValidation";
    }

    @PostMapping("/receivedCreditDocuments")
    public String receivedDocuments(@RequestParam("q1") String q1,
                              @RequestParam("q2") String q2) {
        if (q1.equals("ja") && q2.equals("ja")) {
            return "redirect:/creditValidation";
        } else {
            return "home/creditDocumentation";
        }
    }

    @GetMapping("/KPICar")
    public String totalRentedCars (Model model) {
        List<Car> totalRentedCarsList = kpiService.totalRentedCars();
        model.addAttribute("totalRentedCars", totalRentedCarsList);

        List<Car>  totalavailabelCars = kpiService.totalavailabelCars();
        model.addAttribute("totalavailabelCars",totalavailabelCars);

        List<Car> orderByRentalEndDate = kpiService.orderByRentalEndDate();
        model.addAttribute("orderByRentalEndDate",orderByRentalEndDate);

        List<Contract> findRentalEndDate = kpiService.findRentalEndDate();
        model.addAttribute("findRentalEndDate",findRentalEndDate);

        return "home/KPICar";
    }
    @GetMapping("/stats")
    public String payedNow(Model model) {
        List<Payment> payedNow = kpiService.payedNow();
        model.addAttribute("payedNow",payedNow);

        List<Payment> notPayed = kpiService.notPayed();
        model.addAttribute("notPayed",notPayed);
        return "home/stats";
    }
    @GetMapping("/conditionReportDocumentation")
    public String conditionReportDocumentation() {
        return "home/conditionReportDocumentation";
    }




//hej

    @GetMapping("/creditDocumentation")
    public String creditDocumentation() {
        return "home/creditDocumentation";
    }




    @PostMapping("/creditvalidation-form")
    public String creditvalidationForm() {

        return "home/contract";
    }

   @PostMapping("/payment")
         public String finalizeWithPatyment(@ModelAttribute Payment payment) {
             paymentService.finalizeWithPatyment(payment);
             return "home/payment";
         }

    @GetMapping("/allStaffMembers")
    public String allStaffMembers(Model model) {
        List<StaffMember> staffMemberList = staffMemberService.allStaffMembers();
        model.addAttribute("allStaff",staffMemberList);
        return "home/allStaffMembers";
    }

    @GetMapping("/conditionReportDocumentation")
    public String conditionReportDocumentation() {
        return "home/conditionReportDocumentation";
    }

    @PostMapping("/addStaffMember")
    public String createCustomer(@ModelAttribute StaffMember s) {
        staffMemberService.createStaff(s);
        return "home/createStaffMember";
    }
    @GetMapping("/createStaffMember")
    public String createCustomer() {
        return "home/createStaffMember";
    }
    @PostMapping("/conditionReport")
    public String conditionReport(@RequestParam("contract_id") int contract_id, Model model) {
        model.addAttribute("contract", contractService.findContractById(contract_id));
        return "home/conditionReport";
    }

    @PostMapping("/saveConditionReport")
    public String saveConditionReport(@ModelAttribute ConditionReport conditionReport) {
        conditionReportService.saveConditionReport(conditionReport);
        return "home/frontPage";
    }







}

