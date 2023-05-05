package com.example.bilabonnement.model;

public class Car {

    private String car_id;
    private String car_brand;
    private String  car_model;
    private String  car_plate;
    private double car_odometer;
    private int car_vin;

    public Car(){

    }

    public Car(String car_id, String car_brand, String car_model, String car_plate, double car_odometer, int car_vin) {
        this.car_id = car_id;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_plate = car_plate;
        this.car_odometer = car_odometer;
        this.car_vin = car_vin;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public double getCar_odometer() {
        return car_odometer;
    }

    public void setCar_odometer(double car_odometer) {
        this.car_odometer = car_odometer;
    }

    public int getCar_vin() {
        return car_vin;
    }

    public void setCar_vin(int car_vin) {
        this.car_vin = car_vin;
    }
}
