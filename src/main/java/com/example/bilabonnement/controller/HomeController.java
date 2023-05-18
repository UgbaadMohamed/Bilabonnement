package com.example.bilabonnement.controller;
import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.model.StaffMember;
import com.example.bilabonnement.service.KPIService;
import com.example.bilabonnement.service.PaymentService;
import com.example.bilabonnement.service.StaffMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    StaffMemberService staffService;

    @Autowired
    KPIService kpiService;
    @Autowired
    PaymentService paymentService;


    private int staff_id;

    @GetMapping("/")
    public String frontPage() {
        return "home/allStaffMembers";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "home/loginPage";
    }

    @PostMapping("/loginPage")
    public String loginPage(@RequestParam("staff_member_username") String staff_member_username,
                            @RequestParam("staff_member_password")
                            String staff_member_password, Model model) {
        if (staffService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffService.findStaffMember(staff_member_username,
                    staff_member_password);
            model.addAttribute("staff_member", staffMember);
            return "home/staffType";
        }
        return "home/loginPage";
    }

    @GetMapping("/creditValidation")
    public String creditValidation() {
        return "home/creditValidation";
    }

    @PostMapping("/process-form")
    public String processForm(@RequestParam("q1") String q1,
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
        List<StaffMember> staffMemberList = staffService.allStaffMembers();
        model.addAttribute("allStaff",staffMemberList);
        return "home/allStaffMembers";
    }



}

