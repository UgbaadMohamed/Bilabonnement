package com.example.bilabonnement.model;

public class Car {

    private int car_id;
    private String car_brand;
    private String  car_model;
    private String  car_plate;
    private double car_odometer;
    private int car_vin;
    private String image;
   private int subscription_price;
    private String car_location;

    private int totalPrice;

    private double car_price;
    public Car(){

    }

    public Car(int car_id, String car_brand, String car_model, String car_plate, double car_odometer, int car_vin, String image, int subscription_price, String car_location) {
        this.car_id = car_id;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_plate = car_plate;
        this.car_odometer = car_odometer;
        this.car_vin = car_vin;
        this.image = image;
        this.subscription_price = subscription_price;
        this.car_location = car_location;
        this.car_price = car_price;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSubscription_price() {
        return subscription_price;
    }

    public void setSubscription_price(int subscription_price) {
        this.subscription_price = subscription_price;
    }

    public String getCar_location() {
        return car_location;
    }

    public void setCar_location(String car_location) {
        this.car_location = car_location;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDamagePrice() {
        return totalDamagePrice;
    }

    public void setTotalDamagePrice(int totalDamagePrice) {
        this.totalDamagePrice = totalDamagePrice;
    }

    int totalDamagePrice;


    public double getCar_price()
    {
        //sørger for, at der kun er 2 cifre efter kommaet
        String formattedPrice = String.format("%.2f", car_price);
        formattedPrice = formattedPrice.replace(",", ".");
        car_price = Double.parseDouble(formattedPrice);
        return car_price;
    }

    public void setCar_price(double car_price)
    {
        this.car_price = car_price;
    }
}
