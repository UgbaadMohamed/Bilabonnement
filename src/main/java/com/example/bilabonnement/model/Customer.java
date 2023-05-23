package com.example.bilabonnement.model;

public class Customer {


    private int customer_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_address;
    private int zip_code;
    private String customer_phone_number;
    private String customer_license_number;
    private int customer_age;
    private int customer_creditworthy;

    public Customer(){

    }


    public Customer(int customer_id, String customer_first_name, String customer_last_name, String customer_address,
                    int zip_code, String customer_phone_number, String customer_license_number, int customer_age,
                    int customer_validation) {
        this.customer_id = customer_id;
        this.customer_first_name = customer_first_name;
        this.customer_last_name = customer_last_name;
        this.customer_address = customer_address;
        this.zip_code = zip_code;
        this.customer_phone_number = customer_phone_number;
        this.customer_license_number = customer_license_number;
        this.customer_age = customer_age;
        this.customer_creditworthy = customer_validation;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public int getZip_code() {
        return zip_code;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public String getCustomer_license_number() {
        return customer_license_number;
    }

    public int getCustomer_age() {
        return customer_age;
    }

    public int getCustomer_creditworthy() {
        return customer_creditworthy;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public void setCustomer_phone_number(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    public void setCustomer_license_number(String customer_license_number) {
        this.customer_license_number = customer_license_number;
    }

    public void setCustomer_age(int customer_age) {
        this.customer_age = customer_age;
    }

    public void setCustomer_creditworthy(int customer_creditworthy) {
        this.customer_creditworthy = customer_creditworthy;
    }
}