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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
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
//kk
//Ugbaad
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
                            String staff_member_password, HttpSession session) {
        if (staffMemberService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffMemberService.findStaffMember(staff_member_username,
                    staff_member_password);
            session.setAttribute("staff_member", staffMember);

            return "home/homePage";
        }
        return "home/loginPageDenied";
    }

    @GetMapping("/homePage")
    public String homePage() {
        return "home/homePage";
    }

//----------
//----------MUNIRA og Ikhra-----
    @GetMapping("/search")
    public String searchForCar(@RequestParam("car_brand") String car_brand, Model model) {
        List<Car> cars = carService.searchSpecificCar(car_brand);

        if (cars.isEmpty()) {
            return "redirect:/carLeasing";
        }
        model.addAttribute("cars", cars);

        return "home/search";
    }
//---------
    //----------MUNIRA

    @GetMapping("/carLeasing")
    public String carLeasing(Model model, HttpSession session) {
        List<Car> cars = carService.fetchAvailableCars();
        model.addAttribute("cars", cars);
        //  session.getAttribute("staffmember");

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 1)
            return "home/carLeasing";


        return "redirect:/loginPage";
    }


    @GetMapping("/viewCar/{car_id}")
    public String viewCar(@PathVariable("car_id") int car_id, Model model) {
        Car car = carService.viewCar(car_id);
        model.addAttribute("car", car);
        return "home/carInformation";
    }

    @PostMapping("/carSelected/{car_id}")
    public String saveLocation(@ModelAttribute Car car, @PathVariable("car_id") int car_id, Model model) {
        carService.saveLocation(car.getCar_location(), car_id);
        model.addAttribute("car", carService.findCarById(car_id));

        return "home/customerForm";
    }
//_______
//--------- Ikhra og FREYA-----
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model) {

        model.addAttribute("car", car);

        //vi bruger license number (unique) til at finde customer, da ModelAttribut customer
        // i princippet ikke har nogen customer_id endnu, og derfor vil værdien være 0
        // (da den endnu ikke helt er oprettet endnu med auto increment(i databasen)),
        // og ikke vil kunne anvendes til videre brug. men ved
        // brug af license kan vi finde den pågældende customer,
        // tilføje den til den nye model.addAttribute og anvende den til videre brug

        if (customer.getCustomer_age() < 18 || customerService.checkCustomer(customer.getCustomer_license_number())) {
            return "home/customerForm";
        }
        customerService.createCustomer(customer);
        model.addAttribute("customer",
                customerService.findCustomerByLicense(customer.getCustomer_license_number()));

        return "home/creditDocumentation";
    }

    //--------- UGBAAD og FREYA-----
    @PostMapping("/receivedCreditDocuments")
    public String receivedDocuments(@RequestParam("q1") String q1,
                                    @RequestParam("q2") String q2, @ModelAttribute Car car, @ModelAttribute Customer
                                                customer,
                                    Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);

        if (q1.equals("ja") && q2.equals("ja")) {
            return "home/creditValidation";
        } else {
            return "home/creditDocumentation";
        }
    }

    @PostMapping("/creditValidationSuccess")
    public String creditValidationSuccess(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model) {
        customerService.makeCustomerCreditworthy(customer.getCustomer_id());
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);

        return "home/contract";
    }

//------------
//--------- Munira-----
    @PostMapping("/contractInfo")
    public String contractInfo(@ModelAttribute Contract contract, @ModelAttribute Car car,
                               @ModelAttribute Customer customer, Model model, HttpSession session) {
        session.getAttribute("staffmember");
        if (contractService.makeContract(contract, car.getCar_id(), customer.getCustomer_id()) == true) {
            Contract con = contractService.findContractByCarId(car.getCar_id());

            model.addAttribute("contract", con);

            int sum = contractService.totalPriceForMonthlyPayment(con.getContract_id());
            model.addAttribute("totalPriceForPayment", sum);
            return "home/payment";
        } else
            return "home/contract";
    }
    //--- Ugbaad og Munira------

    @PostMapping("/payment")
    public String finalizeWithPayment(Model model, @ModelAttribute Payment payment, @ModelAttribute Contract contract, HttpSession session) {
        paymentService.finalizeWithPayment(payment, contract);
        session.getAttribute("staffmember");
        return "home/homePage";
    }

    //-----
    //--- Ugbaad og Freya------
    @GetMapping("/conditionReportDocumentation")
    public String conditionReportDocumentation(Model model, HttpSession session) {
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 3)
            return "home/conditionReportDocumentation";

        return "redirect:/loginPage";

    }

    @PostMapping("/conditionReport")
    public String conditionReport(@RequestParam("contract_id") int contract_id, Model model, HttpSession session) {

        session.getAttribute("staffmember");
        try {
            if (conditionReportService.checkIfAlreadyConditionReported(contract_id)) {
                return "home/ConditionReportDenied";
            } else {
                model.addAttribute("contract", contractService.findContractById(contract_id));

                return "home/conditionReport";
            }
        } catch (EmptyResultDataAccessException e) {
            return "home/ConditionReportDenied";
        }

    }

    @PostMapping("/saveConditionReport")
    public String saveConditionReport(Model model, @ModelAttribute ConditionReport conditionReport, HttpSession session) {

        conditionReportService.saveConditionReport(conditionReport);
        return "home/homePage";
    }





/*-------------------------- */
    //--- Ugbaad og Munira ------

    @GetMapping("/KPICar")
    public String totalRentedCars(Model model, HttpSession session) {
        List<Car> totalRentedCarsList = kpiService.totalRentedCars();
        model.addAttribute("totalRentedCars", totalRentedCarsList);

        List<Car> totalavailabelCars = kpiService.totalavailabelCars();
        model.addAttribute("totalavailabelCars", totalavailabelCars);

        List<Car> orderByRentalEndDate = kpiService.orderByRentalEndDate();
        model.addAttribute("orderByRentalEndDate", orderByRentalEndDate);

        List<Contract> findRentalEndDate = kpiService.findRentalEndDate();
        model.addAttribute("findRentalEndDate", findRentalEndDate);

        Map<Contract, Car> map = kpiService.mapOfcontractsandcar();
        model.addAttribute("list", map);
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2)
            return "home/KPICar";


        return "redirect:/loginPage";
    }


    @GetMapping("/KPIEconomy")
    public String payedNow(Model model, HttpSession session) {
        List<Payment> payedNow = kpiService.payedNow();
        model.addAttribute("payedNow", payedNow);

        List<Payment> notPayed = kpiService.notPayed();
        model.addAttribute("notPayed", notPayed);

        int total = carService.totalMonthlyPrice();
        model.addAttribute("subscriptionPrice", total);


        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2)
            return "home/KPIEconomy";


        return "redirect:/loginPage";

    }
    //--------------

    //REVIEW--------------------- IKHRA OG FREYA

    @GetMapping("/findReviewTarget")
    public String findReviewTarget(Model model, HttpSession session) {


        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2)
            return "home/findReviewTarget";


        return "redirect:/loginPage";

    }

    @GetMapping("/review")
    public String review(@RequestParam("contract_id") int contract_id, Model model, HttpSession session) {
        try {
            if (reviewService.checkIfAlreadyReviewed(contract_id)) {
                return "home/reviewDenied";
            } else {
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


        /* contract id er blevet overført som en hidden value, og kan nu bruges til at finde den
            pågældende bil (gennem en join) */
        Car car = carService.findCarByContractId(review.getContract_id());
        model.addAttribute("car", car);



        if (review.getBuying_customer() == 1)
            return "home/carSale";
        else
        return "home/homePage";
    }

    @PostMapping("/sellCar")
    public String sellCar(Car car) {

        carService.sellCar(car);
        return "home/homePage";
    }

    @PostMapping("/priceConverter")
    public String convertPrice(@ModelAttribute Car car, Model model,
                               @RequestParam("currency") String currency, Review review,
                               Contract contractn) {
        car = carService.findCarByContractId(car.getCar_id());
        model.addAttribute("car", car);
        carService.convertPrice(car, currency);

        return "home/carSale";
    }

    @PostMapping("/carSaleDenied")
    public String carSaleDenied(@ModelAttribute Car car) {
        reviewService.carSaleDenied(car);
        return "home/homePage";
    }

    @GetMapping("/auction")
    public String auction(Model model, HttpSession session) {
        List<Car> carsInAuction = carService.fetchCarsInAuction();
        model.addAttribute("cars_in_auction", carsInAuction);

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2)
            return "home/auction";


        return "redirect:/loginPage";


    }

    //------------------------------


    //--- Ugbaad ------------
    @GetMapping("/createStaffMember")
    public String createCustomer(HttpSession session) {
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/createStaffMember";

        return "redirect:/loginPage";


    }

    @PostMapping("/addStaffMember")
    public String createCustomer(@ModelAttribute StaffMember s) {

        staffMemberService.createStaff(s);
        return "home/homePage";
    }

    @GetMapping("/deleteStaffMember/{staff_member_id}")
    public String deleteStaffMember(@PathVariable("staff_member_id") int staff_member_id, HttpSession session) {
        boolean deleted = staffMemberService.deleteStaffMember(staff_member_id);
        if (deleted) {
            return "redirect:/homePage";
        } else {
            return "redirect:/homePage";
        }
    }


    @GetMapping("/allStaffMembers")
    public String allStaffMembers(Model model, HttpSession session) {
        List<StaffMember> staffMemberList = staffMemberService.allStaffMembers();
        model.addAttribute("allStaff", staffMemberList);

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/allStaffMembers";

        return "redirect:/loginPage";


    }
    //--------Ikhra----------

    @GetMapping("/customerPage")
    public String customerPage(Model model, HttpSession session) {
        List<Customer> customerList = customerService.fetchAllCustomer();
        model.addAttribute("customers", customerList);
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/customerPage";

        return "redirect:/loginPage";

    }

//----------------
//----------------Munira
    @GetMapping("/viewContracts")
    public String viewContract(Model model, HttpSession session) {
        Map<Contract, Car> map = contractService.mapOfcontractsandcar();
        model.addAttribute("list", map);

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 1 || staffMember.getMember_type_id() == 2)
            return "home/viewContracts";

        return "redirect:/loginPage";


    }


    @GetMapping("/deleteContract/{contract_id}")
    public String deleteContract(@PathVariable("contract_id") int contract_id) {
        boolean deleted = contractService.deleteContract(contract_id);
        if (deleted) {
            return "redirect:/homePage";
        } else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("/fleet")
    public String fleet() {
        return "home/buyCar";
    }

    @PostMapping("/buyCar")
    public String buyCar(@ModelAttribute Car car, HttpSession session) {
        carService.buyCar(car);

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 1 || staffMember.getMember_type_id() == 2)

            return"redirect:/homePage";

        return "redirect:/loginPage";

}

//------------------



}






