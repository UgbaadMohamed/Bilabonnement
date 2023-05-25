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
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CarService carService;
    @Autowired
    ContractService contractService;
    @Autowired
    StaffMemberService staffMemberService;
    @Autowired
    KPIService kpiService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ConditionReportService conditionReportService;
    @Autowired
    ContractService contractservice;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/")
    public String frontPage() {
        return "home/frontPage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "home/loginPage";
    }

    @PostMapping("/loginPage")
    public String loginPage(@RequestParam("staff_member_username") String staff_member_username,
                            @RequestParam("staff_member_password")
                            String staff_member_password, Model model) {
        if (staffMemberService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffMemberService.findStaffMember(staff_member_username,
                    staff_member_password);
            model.addAttribute("staff_member", staffMember);
            return "home/homePage";
        }
        return "home/loginPage";
    }

    @GetMapping("/homePage")
    public String homePage(@ModelAttribute StaffMember staffMember, Model model) {
        model.addAttribute("staff_member", staffMember);
        return "home/homePage";
    }

    @GetMapping("/search")
    public String searchForCar(@RequestParam("car_brand") String car_brand, Model model, @ModelAttribute StaffMember staffMember) {
        List<Car> cars= carService.searchSpecificCar(car_brand);
        System.out.println(cars);
        model.addAttribute("cars", cars);
        model.addAttribute("staff_member", staffMember);

        return "home/search";
    }

    @GetMapping("/carLeasing")
    public String carLeasing(Model model,@ModelAttribute StaffMember staffMember) {
        List<Car> cars = carService.fetchAvailableCars();
        model.addAttribute("cars", cars);
        model.addAttribute("staff_member", staffMember);
        return "home/carLeasing";
    }

    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id, Model model, @ModelAttribute StaffMember staffMember) {
        List<Car> cars = carService.viewCars(car_id);
        model.addAttribute("cars", cars);
        model.addAttribute("staff_member", staffMember);
        return "home/carInformation";
    }

    @PostMapping("/carSelected/{car_id}")
    public String pickLocation(@ModelAttribute Car car, @PathVariable("car_id") int car_id, Model model,@ModelAttribute StaffMember staffMember) {
        carService.location(car.getCar_location(), car_id);
        model.addAttribute("car", carService.findCarById(car_id));
        model.addAttribute("staff_member", staffMember);
        return "home/customerForm";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model,@ModelAttribute StaffMember staffMember) {
        customerService.createCustomer(customer);
        model.addAttribute("car", car);
        model.addAttribute("customer",
                customerService.findCustomerByLicense(customer.getCustomer_license_number()));
        model.addAttribute("staff_member", staffMember);

                //vi bruger license number (unique) til at finde customer, da ModelAttribut customer
                // i princippet ikke har nogen customer_id endnu, og derfor vil værdien være 0
                // (da den endnu ikke helt er oprettet endnu med auto increment(i databasen)),
                // og ikke vil kunne anvendes til videre brug. men ved
                // brug af license kan vi finde den pågældende customer,
                // tilføje den til den nye model.addAttribute og anvende den til videre brug


        return "home/creditDocumentation";
    }

    @PostMapping("/receivedCreditDocuments")
    public String receivedDocuments(@RequestParam("q1") String q1,
                                    @RequestParam("q2") String q2, @ModelAttribute Car car, @ModelAttribute Customer
                                    customer,
                                    Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("staff_member", staffMember);
        if (q1.equals("ja") && q2.equals("ja")) {
            return "home/creditValidation";
        } else {
            return "home/creditDocumentation";
        }
    }
    @PostMapping("/creditValidationSuccess")
    public String creditValidationSuccess(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model,@ModelAttribute StaffMember staffMember) {
        customerService.makeCustomerCreditworthy(customer.getCustomer_id());
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("staff_member", staffMember);
        return "home/contract";
    }



    @PostMapping("/contractInfo")
    public String contractInfo(@ModelAttribute Contract contract, @ModelAttribute Car car,
                               @ModelAttribute Customer customer, Model model,@ModelAttribute StaffMember staffMember) {
        if(contractService.makeContract(contract, car.getCar_id(), customer.getCustomer_id()) == true) {

            model.addAttribute("contract", contractService.findContractByCarId(car.getCar_id()));
            model.addAttribute("staff_member", staffMember);


            return "home/payment";
        }
        else

        return "home/contract";
    }

    @PostMapping("/payment")
    public String finalizeWithPayment(Model model,@ModelAttribute Payment payment, @ModelAttribute Contract contract,@ModelAttribute StaffMember staffMember) {
        paymentService.finalizeWithPayment(payment, contract);
        model.addAttribute("staff_member", staffMember);
        return "home/homePage";
    }

    @GetMapping("/totalPriceForPayment")
    public String totalPayment(@PathVariable("car_id") int car_id,Contract contract, Model model,@ModelAttribute StaffMember staffMember) {
        int sum = contractService.totalPriceForMonthlyPayment(car_id, contract) ;
        System.out.println(sum);
        model.addAttribute("contract", sum);
        model.addAttribute("staff_member", staffMember);
        return "home/contract";
    }

    @GetMapping("/conditionReportDocumentation")
    public String conditionReportDocumentation(Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("staff_member", staffMember);
        return "home/conditionReportDocumentation";
    }

    @PostMapping("/conditionReport")
    public String conditionReport(@RequestParam("contract_id") int contract_id, Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("contract", contractService.findContractById(contract_id));
        model.addAttribute("staff_member", staffMember);
        return "home/conditionReport";
    }

    @PostMapping("/saveConditionReport")
    public String saveConditionReport(Model model,@ModelAttribute ConditionReport conditionReport,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("staff_member", staffMember);
        conditionReportService.saveConditionReport(conditionReport);
        return "home/homePage";
    }

    @GetMapping("/KPICar")
    public String totalRentedCars (Model model, @ModelAttribute StaffMember staffMember) {
        List<Car> totalRentedCarsList = kpiService.totalRentedCars();
        model.addAttribute("totalRentedCars", totalRentedCarsList);

        List<Car> totalavailabelCars = kpiService.totalavailabelCars();
        model.addAttribute("totalavailabelCars",totalavailabelCars);

        List<Car> orderByRentalEndDate = kpiService.orderByRentalEndDate();
        model.addAttribute("orderByRentalEndDate",orderByRentalEndDate);

        List<Contract> findRentalEndDate = kpiService.findRentalEndDate();
        model.addAttribute("findRentalEndDate",findRentalEndDate);

        model.addAttribute("staff_member", staffMember);
        return "home/KPICar";
    }


    @GetMapping("/KPIEconomy")
    public String payedNow(Model model,@ModelAttribute StaffMember staffMember) {
        List<Payment> payedNow = kpiService.payedNow();
        model.addAttribute("payedNow",payedNow);

        List<Payment> notPayed = kpiService.notPayed();
        model.addAttribute("notPayed",notPayed);

        int total= carService.totalMonthlyPrice();
        model.addAttribute("subscriptionPrice", total);

        model.addAttribute("staff_member", staffMember);

        return "home/KPIEconomy";
    }

    //REVIEW---------------------

    @GetMapping("/findReviewTarget")
    public String findReviewTarget(Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("staff_member", staffMember);
            return "home/findReviewTarget";
    }

    @GetMapping("/review")
    public String review(@RequestParam("contract_id") int contract_id, Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("staff_member", staffMember);
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
    public String submitReview(Review review, Model model,@ModelAttribute StaffMember staffMember) {
        reviewService.addReview(review);
        //deklaration
        List<Car> carsInAuction;

        /* contract id er blevet overført som en hidden value, og kan nu bruges til at finde den
            pågældende bil (gennem en join) */
        Car car = carService.findCarByContractId(review.getContract_id());
        model.addAttribute("car", car);
        model.addAttribute("staff_member", staffMember);

        if (review.getBuying_customer() == 1)
            return "home/carSale";
        else
            carsInAuction = carService.fetchCarsInAuction();
            model.addAttribute("cars_in_auction", carsInAuction);
            return "home/homePage";
    }

    @PostMapping("/sellCar")
    public String sellCar(Model model,Car car,@ModelAttribute StaffMember staffMember) {
        carService.sellCar(car);
        model.addAttribute("staff_member", staffMember);
        return "home/homePage";
    }

    @PostMapping("/priceConverter")
    public String convertPrice(@ModelAttribute Car car, Model model,
                               @RequestParam("currency") String currency, Review review,
                               Contract contract,@ModelAttribute StaffMember staffMember) {
        car = carService.findCarByContractId(car.getCar_id());
        model.addAttribute("car", car);
        carService.convertPrice(car, currency);
        model.addAttribute("staff_member", staffMember);

        return "home/carSale";
    }

    @PostMapping("/carSaleDenied")
    public String carSaleDenied(Model model,@ModelAttribute Car car,@ModelAttribute StaffMember staffMember) {
        reviewService.carSaleDenied(car);
        model.addAttribute("staff_member", staffMember);
        return "home/homePage";
    }

    @GetMapping("/auction")
    public String auction(Model model,@ModelAttribute StaffMember staffMember) {
        List<Car> carsInAuction = carService.fetchCarsInAuction();
        model.addAttribute("cars_in_auction", carsInAuction);
        model.addAttribute("staff_member", staffMember);
        return "home/auction";
    }

    //------------------------------

    @GetMapping("/createStaffMember")
    public String createCustomer(Model model,@ModelAttribute StaffMember staffMember) {
        model.addAttribute("staff_member", staffMember);
        return "home/createStaffMember";
    }

    @PostMapping("/addStaffMember")
    public String createCustomer(Model model,@ModelAttribute StaffMember s,@ModelAttribute StaffMember staffMember) {
        staffMemberService.createStaff(s);
        model.addAttribute("staff_member", staffMember);
        return "home/createStaffMember";
    }

    @GetMapping("/allStaffMembers")
    public String allStaffMembers(Model model,@ModelAttribute StaffMember staffMember) {
        List<StaffMember> staffMemberList = staffMemberService.allStaffMembers();
        model.addAttribute("allStaff",staffMemberList);
        model.addAttribute("staff_member", staffMember);
        return "home/allStaffMembers";
    }

    @GetMapping("/customerPage")
    public String customerPage(Model model,@ModelAttribute StaffMember staffMember) {
        List<Customer> customerList = customerService.fetchAllCustomer();
        model.addAttribute("customers", customerList);
        model.addAttribute("staff_member", staffMember);
            return "home/customerPage";
        }

    @GetMapping("/viewLeasedCars/{contract_id}")
    public String viewContract(@PathVariable("contract_id") int contract_id,Model model) {
        List<Contract> contracts =contractService.viewLeasedCars(contract_id);
        model.addAttribute("contracts", contracts);
        System.out.println(contracts);
        return "home/contract";
    }


}





