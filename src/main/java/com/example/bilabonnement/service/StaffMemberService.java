package com.example.bilabonnement.service;
import com.example.bilabonnement.model.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffMemberService {

        @Autowired
        com.example.bilabonnement.repository.StaffMemberRepo staffMemberRepo;


    public Boolean validateLogin(String staff_member_username, String staff_member_password) {
        return staffMemberRepo.validateLogin(staff_member_username, staff_member_password);
    }

    public int getMemberType(String staff_member_username, String staff_member_password) {
        return staffMemberRepo.getMemberType(staff_member_username,staff_member_password);
    }

    public StaffMember findStaffMember(String staff_member_username, String staff_member_password)
    {
        return staffMemberRepo.findStaffMember(staff_member_username,staff_member_password);
    }

    public Boolean deleteStaffMember(int staff_member_id){
        return staffMemberRepo.deleteStaffMember(staff_member_id);
    }


    public List<StaffMember> allStaffMembers() {
        return staffMemberRepo.allStaffMembers();
    }

    public void createStaff(StaffMember s) {
        staffMemberRepo.createStaff(s);
    }

}
