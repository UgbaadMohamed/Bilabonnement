package com.example.bilabonnement.model;

public class Payment {
    private int payment_id;
    private boolean payed;
    private String card_number;

    private int registration_number;

    private int contract_id;

    public Payment() {

    }
    public Payment(int payment_id, boolean payed, String card_number, int registration_number, int contract_id) {
        this.payment_id = payment_id;
        this.payed = payed;
        this.card_number = card_number;
        this.registration_number = registration_number;
        this.contract_id = contract_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
}