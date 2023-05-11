package com.example.bilabonnement.model;

public class StaffMember {
    private int staff_member_id;

    private String staff_member_first_name;

    private String staff_member_last_name;

    private String staff_member_username;

    private String staff_member_password;

    private int member_type_id;


    public StaffMember() {

    }

    public StaffMember(int staff_member_id, String staff_member_first_name,
                       String staff_member_last_name, String staff_member_username, String staff_member_password,int member_type_id) {
        this.staff_member_id = staff_member_id;
        this.staff_member_first_name = staff_member_first_name;
        this.staff_member_last_name = staff_member_last_name;
        this.staff_member_username = staff_member_username;
        this.staff_member_password = staff_member_password;
        this.member_type_id=member_type_id;
    }

    public int getStaff_member_id() {
        return staff_member_id;
    }

    public void setStaff_member_id(int staff_member_id) {
        this.staff_member_id = staff_member_id;
    }

    public String getStaff_member_first_name() {
        return staff_member_first_name;
    }

    public void setStaff_member_first_name(String staff_member_first_name) {
        this.staff_member_first_name = staff_member_first_name;
    }

    public String getStaff_member_last_name() {
        return staff_member_last_name;
    }

    public void setStaff_member_last_name(String staff_member_last_name) {
        this.staff_member_last_name = staff_member_last_name;
    }

    public String getStaff_member_username() {
        return staff_member_username;
    }

    public void setStaff_member_username(String staff_member_username) {
        this.staff_member_username = staff_member_username;
    }

    public String getStaff_member_password() {
        return staff_member_password;
    }

    public void setStaff_member_password(String staff_member_password) {
        this.staff_member_password = staff_member_password;
    }

    public int getMember_type_id() {
        return member_type_id;
    }

    public void setMember_type_id(int member_type_id) {
        this.member_type_id = member_type_id;
    }
}
