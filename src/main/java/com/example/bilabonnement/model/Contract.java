package com.example.bilabonnement.model;

import java.time.LocalDate;
import java.time.temporal.Temporal;

public class Contract {
    private int contract_id;
    private int car_id;
    private LocalDate contract_start_date;
    private LocalDate contract_end_date;
    private int contract_maximum_km;
    private int totalPriceForPayment;
    private int customer_id;


    public Contract() {
    }

    public Contract(int contract_id, LocalDate contract_start_date, LocalDate contract_end_date,
                    int contract_maximum_km, int car_id) {
        this.contract_id = contract_id;
        this.contract_start_date = contract_start_date;
        this.contract_end_date = contract_end_date;
        this.contract_maximum_km = contract_maximum_km;
        this.car_id=car_id;
    }

    public int getTotalPriceForPayment() {
        return totalPriceForPayment;
    }

    public void setTotalPriceForPayment(int totalPriceForPayment) {
        this.totalPriceForPayment = totalPriceForPayment;
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

    public LocalDate getContract_start_date() {
        return contract_start_date;
    }

    public void setContract_start_date(LocalDate contract_start_date) {
        this.contract_start_date = contract_start_date;
    }

    public LocalDate getContract_end_date() {
        return contract_end_date;
    }

    public void setContract_end_date(LocalDate contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    public int getContract_maximum_km() {
        return contract_maximum_km;
    }

    public void setContract_maximum_km(int contract_maximum_km) {
        this.contract_maximum_km = contract_maximum_km;
    }



    @Override
    public String toString() {
        return "Contract{" +
                "contract_id=" + contract_id +
                ", contract_start_date='" + contract_start_date + '\'' +
                ", contract_end_date='" + contract_end_date + '\'' +
                ", contract_maximum_km=" + contract_maximum_km +
                '}';
    }
}
