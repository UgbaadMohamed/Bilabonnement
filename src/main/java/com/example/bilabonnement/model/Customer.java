package com.example.bilabonnement.model;

public class Customer {

    //Attributts

    private int customer_id;
    private String customer_name;
    private String customer_address;
    private int customer_zip;
    private String customer_phone_number;
    private int customer_license_number;
    private int customer_driver_since_date;
    private int customer_validation;



    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public int getCustomer_zip() {
        return customer_zip;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public int getCustomer_license_number() {
        return customer_license_number;
    }

    public int getCustomer_driver_since_date() {
        return customer_driver_since_date;
    }

    public int getCustomer_validation() {
        return customer_validation;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public void setCustomer_zip(int customer_zip) {
        this.customer_zip = customer_zip;
    }

    public void setCustomer_phone_number(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    public void setCustomer_license_number(int customer_license_number) {
        this.customer_license_number = customer_license_number;
    }

    public void setCustomer_driver_since_date(int customer_driver_since_date) {
        this.customer_driver_since_date = customer_driver_since_date;
    }

    public void setCustomer_validation(int customer_validation) {
        this.customer_validation = customer_validation;
    }
}

