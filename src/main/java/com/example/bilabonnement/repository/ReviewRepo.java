package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepo {

    @Autowired
    private JdbcTemplate template;

    public void addReview(Review review) {
        String sql = "INSERT INTO review (review_id, rating, staying_customer, buying_customer, contract_id)" +
                " VALUES (?, ?, ?, ?, ?)";
        template.update(sql, review.getReview_id(), review.getRating(), review.getStaying_customer(),
                review.getBuying_customer(), review.getContract_id());
    }

    public Boolean checkIfAlreadyReviewed(int contract_id) {
        String sql = "SELECT contract_id FROM review WHERE contract_id = ?";
        RowMapper<Review> rowMapper = new BeanPropertyRowMapper<>(Review.class);
        try {
            Review review = template.queryForObject(sql, rowMapper, contract_id);
            if (review == null) {
                return false;
            }
            else
                return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Review findReviewByContractId(int contract_id) {
        String sql = "SELECT review_id FROM review WHERE contract_id = ?";
        RowMapper<Review> rowMapper = new BeanPropertyRowMapper<>(Review.class);

        Review review = template.queryForObject(sql, rowMapper, contract_id);
        return review;
    }

    public void carSaleDenied(Car car) {
        //hvis en kunde ikke vil købe en bil alligevel, bliver buying_customer
        //i review = 0, ved brug af car_id og joins
        String sql = "UPDATE review r " +
                "JOIN contract c ON r.contract_id = c.contract_id " +
                "JOIN car ca ON c.car_id = ca.car_id " +
                "SET r.buying_customer = 0 " +
                "WHERE ca.car_id = ?";

        template.update(sql, car.getCar_id());
    }
}


