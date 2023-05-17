package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CarRepo {

@Autowired
JdbcTemplate template;

    public List<Car> fetchCars(){
        String sql = "SELECT * From car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
         List<Car> cars = template.query(sql, rowMapper);
        //Iterate over each car object and load image from database that = car_id
        for (Car car: cars) {
            int id = car.getCar_id();
            String sql2 = "SELECT image FROM car WHERE car_id = ?";
          byte[] image = template.queryForObject(sql2,byte[].class, id);
          car.setImage(image);
        }
        return cars;
    }

    public Car findCarByContractId(int contract_id){
        String sql = "SELECT car.car_id, car_brand, car_model, car_plate, car_odometer, " +
                "car_vin, car_location, car_price FROM car JOIN contract ON car.car_id = contract.car_id" +
                " WHERE contract_id = ?";

        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        Car car = template.queryForObject(sql, rowMapper, contract_id);
        return car;
    }

    public List<Car> viewCars(int car_id) {
        String sql = "SELECT * From car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_id);
    }

    public void chooseRentingPeriod(Car car, String start_date, String end_date) {
        String sql = "UPDATE car SET start_date =?, end_date =? WHERE car_id =?";
        template.update(sql, start_date, end_date, car.getCar_id()) ;
    }
    public void location(Car car, String location) {
        String sql = "UPDATE car SET location = ?";
        template.update(sql, location, car.getCar_id());
    }

    public List<Car> fetchCarsInAuction() {
        String sql = "SELECT car.car_id, car_brand, car_model FROM car JOIN contract ON car.car_id = contract.car_id" +
                " JOIN review ON contract.contract_id = review.contract_id WHERE buying_customer = ?";

        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carsInAuction = template.query(sql, rowMapper, 0);

        return carsInAuction;
    }

    public Boolean sellCar(Review review, Contract contract, Car car) {
        String deleteReviewSql = "DELETE FROM review WHERE contract_id = ?";
        template.update(deleteReviewSql, review.getContract_id());

        String deleteContract ="DELETE FROM contract WHERE contract_id = ?";
        template.update(deleteContract, contract.getContract_id());

        String sql = "DELETE FROM car WHERE car_id = ?";
        return template.update(sql, car.getCar_id()) > 0;
    }
}