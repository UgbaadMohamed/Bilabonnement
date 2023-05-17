package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Review;
import com.example.bilabonnement.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;

    public void addReview(Review review){
        reviewRepo.addReview(review);
    }

    public Boolean checkIfAlreadyReviewed(int contract_id){
        return reviewRepo.checkIfAlreadyReviewed(contract_id);
    }

    public Review findReviewByContractId(int contract_id){
        return reviewRepo.findReviewByContractId(contract_id);
    }
}
