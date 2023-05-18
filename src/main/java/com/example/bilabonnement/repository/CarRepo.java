package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Repository
public class CarRepo {

@Autowired
JdbcTemplate template;

    public List<Car> fetchCars(){
        String sql = "SELECT * From car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
         List<Car> cars = template.query(sql, rowMapper);
        for (Car c : cars) {
            String sql2 = "Select SUM(subscription_price) FROM car";
            int total = template.queryForObject(sql2, Integer.class);
            c.setTotalPrice(total);
        }
        return cars;
    }

    public List<Car> viewCars(int car_id) {
        String sql = "SELECT * From car where car_id=?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_id);
    }



    public void totalDamagePrice(Car car) {
            String sql = "Select SUM(damage_price) FROM damage_level";
           // int total = template.query(sql, Integer.class);
            //car.setTotalDamagePrice(total);
    }



    public void chooseRentingPeriod(Car car, String start_date, String end_date) {
        String sql = "UPDATE car SET start_date =?, end_date =? WHERE car_id =?";
        template.update(sql, start_date, end_date, car.getCar_id()) ;
    }


    public Car findPersonById(int car_id){
        String sql="SELECT * FROM car WHERE car_id = ?";
        RowMapper<Car> rowMapper= new BeanPropertyRowMapper<>(Car.class);
        Car car= template.queryForObject(sql, rowMapper, car_id);
        return car;
    }
    public void location(String car_location, int car_id) {
        System.out.println(car_id);
        String sql = "UPDATE car SET car_location = ? Where car_id = ?";
        template.update(sql, car_location, car_id);
    }


    public List<Car> searchSpecificCar(String car_brand){
       String sql = "SELECT * FROM car WHERE car_brand = ?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_brand);
    }


}
