package com.example.bilabonnement.repo;

import com.example.bilabonnement.model.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StaffMemberRepo {

    @Autowired
    private JdbcTemplate template;

    public boolean validateLogin(String staff_member_username, String staff_member_password) {
        String sql = "SELECT staff_member_username, member_type_id, staff_member_password FROM staff_member WHERE staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);
        try {
            StaffMember staffMember = template.queryForObject(sql, rowMapper, staff_member_username, staff_member_password);
            return staffMember != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public StaffMember getStaffMember(String staff_member_username, String staff_member_password) {
        String sql = "SELECT * FROM staff_member WHERE staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);

        StaffMember staffMember = template.queryForObject(sql, rowMapper, staff_member_username, staff_member_password);
        return  staffMember;
    }



    public int getMemberType(String staff_member_username, String staff_member_password) {
        String sql = "SELECT member_type_id FROM staff_member WHERE staff_member_username = ? AND staff_member_password = ?";
        RowMapper<StaffMember> rowMapper = new BeanPropertyRowMapper<>(StaffMember.class);
        StaffMember staffMember = template.queryForObject(sql, rowMapper, staff_member_username, staff_member_password);
        return staffMember.getMember_type_id();
    }
}
