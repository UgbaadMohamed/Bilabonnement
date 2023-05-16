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
        return cars;
    }

    public List<Car> viewCars(int car_id) {
        String sql = "SELECT * From car where car_id=?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_id);
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


    public List<Car> searchSpecificCar(String car_model){
        String sql= "SELECT * FROM car WHERE car_model = ?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, car_model);
    }




   /* @PostMapping("/pickLocation")
    public String pickLocation(@ModelAttribute Car car) {
        System.out.println(car.getCar_location());
        carService.location(car.getCar_location(),car.getCar_id(), car);
        return "redirect:/";
    }*/





}
