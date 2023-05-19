package com.example.bilabonnement.model;

public class Contract {
    private int contract_id;
    private int customer_id;
    private int car_id;
    private String contract_start_date;
    private String contract_end_date ;
    private int contract_maximum_km;
    private int contract_start_km;

    public Contract() {
    }

    public Contract(int contract_id, int customer_id, int car_id, String contract_start_date, String contract_end_date, int contract_maximum_km, int contract_start_km)
    {
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.car_id = car_id;

    private int car_id;

    private String contract_start_date;
    private String contract_end_date;
    private int contract_maximum_km;
    private int contract_start_km;


    public Contract() {
    }

    public Contract(int contract_id, String contract_start_date, String contract_end_date, int contract_maximum_km, int contract_start_km, int car_id) {
        this.contract_id = contract_id;
        this.contract_start_date = contract_start_date;
        this.contract_end_date = contract_end_date;
        this.contract_maximum_km = contract_maximum_km;
        this.contract_start_km = contract_start_km;
        this.car_id = car_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_start_date() {
        return contract_start_date;
    }

    public void setContract_start_date(String contract_start_date) {
        this.contract_start_date = contract_start_date;
        this.contract_end_date = contract_end_date;
        this.contract_maximum_km = contract_maximum_km;
        this.contract_start_km = contract_start_km;
    }

    public int getContract_id()
    {
        return contract_id;
    }

    public void setContract_id(int contract_id)
    {
        this.contract_id = contract_id;
    }

    public int getCustomer_id()
    {
        return customer_id;
    }

    public void setCustomer_id(int customer_id)
    {
        this.customer_id = customer_id;
    }

    public int getCar_id()
    {
        return car_id;
    }

    public void setCar_id(int car_id)
    {
        this.car_id = car_id;
    }

    public String getContract_start_date()
    {
        return contract_start_date;
    }

    public void setContract_start_date(String contract_start_date)
    {
        this.contract_start_date = contract_start_date;
    }

    public String getContract_end_date()
    {
        return contract_end_date;
    }

    public void setContract_end_date(String contract_end_date)
    {
        this.contract_end_date = contract_end_date;
    }

    public int getContract_maximum_km()
    {
        return contract_maximum_km;
    }

    public void setContract_maximum_km(int contract_maximum_km)
    {
        this.contract_maximum_km = contract_maximum_km;
    }

    public int getContract_start_km()
    {
        return contract_start_km;
    }

    public void setContract_start_km(int contract_start_km)
    {
        this.contract_start_km = contract_start_km;
    }
}

}