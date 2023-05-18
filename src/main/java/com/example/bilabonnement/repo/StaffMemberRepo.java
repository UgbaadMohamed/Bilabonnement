package com.example.bilabonnement.repo;

import com.example.bilabonnement.model.Payment;
import com.example.bilabonnement.model.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffMemberRepo {
    @Autowired
    private JdbcTemplate template;
//findes der dette login
    public boolean validateLogin(String staff_member_username, String staff_member_password) {
        String sql = "SELECT staff_member_username, member_type_id, staff_member_password FROM" +
                " staff_member WHERE staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);
        try {
            StaffMember staffMember = template.queryForObject(sql, rowMapper,
                    staff_member_username, staff_member_password);
            return staffMember != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
//finderbrugeren og returner rækken
    public StaffMember findStaffMember(String staff_member_username, String staff_member_password) {
        String sql = "SELECT * FROM staff_member WHERE staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);

        StaffMember staffMember = template.queryForObject(sql, rowMapper, staff_member_username, staff_member_password);
        return  staffMember;
    }

    // finde memeber typen udfra hvad der står i databasen...

    public int getMemberType(String staff_member_username, String staff_member_password) {
        String sql = "SELECT member_type_id FROM staff_member WHERE " +
                "staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);
        StaffMember staffMember = template.queryForObject(sql, rowMapper,
                staff_member_username, staff_member_password);
        return staffMember.getMember_type_id();
    }


    public List<StaffMember> allStaffMembers() {
        String sql = "SELECT staff_member_first_name, staff_member_last_name FROM staff_member;";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);
        List<StaffMember> staffMemberList = template.query(sql, rowMapper);
        return staffMemberList;
    }


    public void createStaff(StaffMember s) {
        String sql = "INSERT INTO staff_member (staff_member_first_name, staff_member_last_name, staff_member_username, staff_member_password, member_type_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        template.update(sql,s.getStaff_member_first_name(),s.getStaff_member_last_name(),s.getStaff_member_username(),s.getStaff_member_password(),s.getMember_type_id());
    }

}
