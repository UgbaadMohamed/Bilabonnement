package com.example.bilabonnement.service;
import com.example.bilabonnement.model.StaffMember;
import com.example.bilabonnement.repo.StaffMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffMemberService {
    @Autowired
    StaffMemberRepo staffMemberRepo;

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


    public List<StaffMember> allStaffMembers() {
        return staffMemberRepo.allStaffMembers();
    }

    public void createStaff(StaffMember s) {
        staffMemberRepo.createStaff(s);
    }

}
