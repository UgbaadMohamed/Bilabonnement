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
    public String homePage(HttpSession session ) {
        session.getAttribute("staffmember");
        return "home/homePage";
    }


    @GetMapping("/search")
    public String searchForCar(@RequestParam("car_brand") String car_brand, HttpSession session, Model model) {
        List<Car> cars = carService.searchSpecificCar(car_brand);
        System.out.println(cars);
        if (cars.isEmpty()) {
            return "redirect:/carLeasing";
        }
        model.addAttribute("cars", cars);
        session.getAttribute("staffmember");
        return "home/search";
    }


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
    public String viewCar(@PathVariable("car_id") int car_id, Model model,HttpSession session) {
        Car car = carService.viewCar(car_id);
        model.addAttribute("car", car);
        session.getAttribute("staffmember");
        return "home/carInformation";
    }

    @PostMapping("/carSelected/{car_id}")
    public String pickLocation(@ModelAttribute Car car, @PathVariable("car_id") int car_id, Model model,HttpSession session) {
        carService.location(car.getCar_location(), car_id);
        model.addAttribute("car", carService.findCarById(car_id));
        session.getAttribute("staffmember");
        return "home/customerForm";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model,HttpSession session) {
        session.getAttribute("staffmember");
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

    @PostMapping("/receivedCreditDocuments")
    public String receivedDocuments(@RequestParam("q1") String q1,
                                    @RequestParam("q2") String q2, @ModelAttribute Car car, @ModelAttribute Customer
                                    customer,
                                    Model model,HttpSession session) {
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        session.getAttribute("staffmember");

        if (q1.equals("ja") && q2.equals("ja")) {
            return "home/creditValidation";
        } else {
            return "home/creditDocumentation";
        }
    }
    @PostMapping("/creditValidationSuccess")
    public String creditValidationSuccess(@ModelAttribute Customer customer, @ModelAttribute Car car, Model model,
                                          HttpSession session) {
        customerService.makeCustomerCreditworthy(customer.getCustomer_id());
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        session.getAttribute("staffmember");
        return "home/contract";
    }



    @PostMapping("/contractInfo")
    public String contractInfo(@ModelAttribute Contract contract, @ModelAttribute Car car,
                               @ModelAttribute Customer customer, Model model, HttpSession session) {
        session.getAttribute("staffmember");
        if (contractService.makeContract(contract, car.getCar_id(), customer.getCustomer_id()) == true) {
            Contract contract2 = contractService.findContractByCarId(car.getCar_id());

            model.addAttribute("contract", contract2);

            int sum = contractService.totalPriceForMonthlyPayment(contract2.getContract_id());
            model.addAttribute("totalPriceForPayment", sum);
            return "home/payment";
        } else
            return "home/contract";
    }

    @PostMapping("/payment")
    public String finalizeWithPayment(Model model,@ModelAttribute Payment payment, @ModelAttribute Contract contract,HttpSession session) {
        paymentService.finalizeWithPayment(payment, contract);
        session.getAttribute("staffmember");
        return "home/homePage";
    }

    @GetMapping("/conditionReportDocumentation")
    public String conditionReportDocumentation(Model model,HttpSession session) {
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 3)
            return "home/conditionReportDocumentation";

        return "redirect:/loginPage";

    }

    @PostMapping("/conditionReport")
    public String conditionReport(@RequestParam("contract_id") int contract_id, Model model,HttpSession session) {

        session.getAttribute("staffmember");
        try {
            if (conditionReportService.checkIfAlreadyConditionReported(contract_id)) {
                return "home/ConditionReportDenied";
            }
            else {
                model.addAttribute("contract", contractService.findContractById(contract_id));

                return "home/conditionReport";
            }
        } catch (EmptyResultDataAccessException e) {
            return "home/ConditionReportDenied";
        }

    }

    @PostMapping("/saveConditionReport")
    public String saveConditionReport(Model model,@ModelAttribute ConditionReport conditionReport,HttpSession session) {
        session.getAttribute("staffmember");
        conditionReportService.saveConditionReport(conditionReport);
        return "home/homePage";
    }



    /* @RequestParam("contract_id") int contract_id,



     */

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
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2 )
            return "home/KPICar";


        return "redirect:/loginPage";
    }



    @GetMapping("/KPIEconomy")
    public String payedNow(Model model,HttpSession session) {
        List<Payment> payedNow = kpiService.payedNow();
        model.addAttribute("payedNow",payedNow);

        List<Payment> notPayed = kpiService.notPayed();
        model.addAttribute("notPayed",notPayed);

        int total= carService.totalMonthlyPrice();
        model.addAttribute("subscriptionPrice", total);

        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2 )
            return "home/KPIEconomy";


        return "redirect:/loginPage";

    }


    //REVIEW---------------------

    @GetMapping("/findReviewTarget")
    public String findReviewTarget(Model model,HttpSession session) {
        session.getAttribute("staffmember");

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2 )
            return "home/findReviewTarget";


        return "redirect:/loginPage";

    }

    @GetMapping("/review")
    public String review(@RequestParam("contract_id") int contract_id, Model model,HttpSession session) {
        session.getAttribute("staffmember");
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
    public String submitReview(Review review, Model model,HttpSession session) {
        reviewService.addReview(review);
        //deklaration
        List<Car> carsInAuction;

        /* contract id er blevet overført som en hidden value, og kan nu bruges til at finde den
            pågældende bil (gennem en join) */
        Car car = carService.findCarByContractId(review.getContract_id());
        model.addAttribute("car", car);
        session.getAttribute("staffmember");


        if (review.getBuying_customer() == 1)
            return "home/carSale";
        else
            carsInAuction = carService.fetchCarsInAuction();
            model.addAttribute("cars_in_auction", carsInAuction);
            return "home/homePage";
    }

    @PostMapping("/sellCar")
    public String sellCar(Car car,HttpSession session) {
        session.getAttribute("staffmember");
        carService.sellCar(car);
        return "home/homePage";
    }

    @PostMapping("/priceConverter")
    public String convertPrice(@ModelAttribute Car car, Model model,
                               @RequestParam("currency") String currency, Review review,
                               Contract contract,HttpSession session) {
        car = carService.findCarByContractId(car.getCar_id());
        model.addAttribute("car", car);
        session.getAttribute("staffmember");
        carService.convertPrice(car, currency);

        return "home/carSale";
    }

    @PostMapping("/carSaleDenied")
    public String carSaleDenied(@ModelAttribute Car car,HttpSession session) {
        session.getAttribute("staffmember");
        reviewService.carSaleDenied(car);
        return "home/homePage";
    }

    @GetMapping("/auction")
    public String auction(Model model,HttpSession session) {
        List<Car> carsInAuction = carService.fetchCarsInAuction();
        model.addAttribute("cars_in_auction", carsInAuction);
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4 || staffMember.getMember_type_id() == 2 )
            return "home/auction";


        return "redirect:/loginPage";


    }

    //------------------------------

    @GetMapping("/createStaffMember")
    public String createCustomer(HttpSession session) {
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/createStaffMember";

        return "redirect:/loginPage";



    }

    @PostMapping("/addStaffMember")
    public String createCustomer(@ModelAttribute StaffMember s,HttpSession session) {
        session.getAttribute("staffmember");
        staffMemberService.createStaff(s);
        return "home/homePage";
    }

    @GetMapping("/deleteStaffMember/{staff_member_id}")
    public String deleteStaffMember(@PathVariable("staff_member_id")int staff_member_id,HttpSession session){
        boolean deleted= staffMemberService.deleteStaffMember(staff_member_id);
        session.getAttribute("staffmember");
        if (deleted) {
            return "redirect:/homePage";
        }
        else {
            return "redirect:/homePage";
        }
    }


    @GetMapping("/allStaffMembers")
    public String allStaffMembers(Model model,HttpSession session) {
        List<StaffMember> staffMemberList = staffMemberService.allStaffMembers();
        model.addAttribute("allStaff",staffMemberList);
        session.getAttribute("staffmember");

        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/allStaffMembers";

        return "redirect:/loginPage";


    }

    @GetMapping("/customerPage")
    public String customerPage(Model model,HttpSession session) {
        List<Customer> customerList = customerService.fetchAllCustomer();
        model.addAttribute("customers", customerList);
        session.getAttribute("staffmember");
        StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
        if (staffMember.getMember_type_id() == 4)
            return "home/customerPage";

        return "redirect:/loginPage";

        }
    




   /*@GetMapping("/viewLeasedCars/{contract_id}")
    public String viewContract(@PathVariable("contract_id") int contract_id,Model model) {
        List<Contract> contracts =contractService.viewLeasedCars(contract_id);
        model.addAttribute("contracts", contracts);
        System.out.println(contracts);
        return "home/viewContracts";
    }*/
   @GetMapping("/viewContracts")
   public String viewContract(Model model,HttpSession session) {
       Map<Contract, Car> map = contractService.mapOfcontractsandcar();
       model.addAttribute("list",map);
       session.getAttribute("staffmember");
       StaffMember staffMember = (StaffMember) session.getAttribute("staff_member");
       //if (staffMember.getMember_type_id() )
          // return "home/viewContracts";

       return "redirect:/loginPage";


}

    @GetMapping("/deleteCustumer/{customer}")
    public String deleteCustumer(@PathVariable("customer")int customer,HttpSession session){
        boolean deleted = customerService.deleteCustomer(customer);
        session.getAttribute("staffmember");
        if (deleted) {
            return "redirect:/homePage";
        }
        else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("/deleteContract/{contract_id}")
    public String deleteContract(@PathVariable("contract_id")int contract_id,HttpSession session){
        boolean deleted= contractService.deleteContract(contract_id);
        session.getAttribute("staffmember");
        if (deleted) {
            return "redirect:/homePage";
        }
        else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("/fleet")
    public String fleet(){
        return "home/buyCar";
    }
    @PostMapping("/buyCar")
    public String buyCar(@ModelAttribute Car car, HttpSession session){
        carService.buyCar(car);
        session.getAttribute("staffmember");
        return "redirect:/homePage";
    }




}






