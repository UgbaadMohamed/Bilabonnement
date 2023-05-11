package com.example.bilabonnement.controller;
import com.example.bilabonnement.model.StaffMember;
import com.example.bilabonnement.service.StaffMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    StaffMemberService staffService;
    private int staff_id;
    @GetMapping("/")
    public String frontPage () {
        return "home/creditDocumentation";
    }

    @PostMapping("/login")
    public String login(@RequestParam("staff_member_username") String staff_member_username, @RequestParam("staff_member_password")
    String staff_member_password, Model model) {
        //model.addAttribute("staff_member", staffService.validateLogin(staff_member_username, staff_member_password));

        if (staffService.validateLogin(staff_member_username, staff_member_password)) {
            StaffMember staffMember = staffService.getStaffMember(staff_member_username, staff_member_password);
            model.addAttribute("staff_member", staffMember);
            return "home/homePage";
        }
        return "home/frontPage";
    }
    @GetMapping ("/creditValidation")
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
    @PostMapping("/creditvalidation-form")
    public String creditvalidationForm() {

        return "home/contract";
    }







}

