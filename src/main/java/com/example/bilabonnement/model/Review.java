package com.example.bilabonnement.model;

public class Review {

    private int review_id;
    private int rating;
    private int staying_customer;
    private int buying_customer;
    private int contract_id;

    public Review(){

    }

    public Review(int review_id, int rating, int staying_customer, int buying_customer, int contract_id) {
        this.review_id = review_id;
        this.rating = rating;
        this.staying_customer = staying_customer;
        this.buying_customer = buying_customer;
        this.contract_id = contract_id;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStaying_customer() {
        return staying_customer;
    }

    public void setStaying_customer(int staying_customer) {
        this.staying_customer = staying_customer;
    }

    public int getBuying_customer() {
        return buying_customer;
    }

    public void setBuying_customer(int buying_customer) {
        this.buying_customer = buying_customer;
    }

    public int getContract_id()
    {
        return contract_id;
    }

    public void setContract_id(int contract_id)
    {
        this.contract_id = contract_id;
    }
}
