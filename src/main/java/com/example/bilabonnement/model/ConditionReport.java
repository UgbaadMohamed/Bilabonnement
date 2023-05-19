package com.example.bilabonnement.model;

public class ConditionReport {
    private int condition_report_id;
    private int odometer_over_limit;

    private int contract_id;
    private int damage_level_id;

    private int odometer_price;

    public ConditionReport() {
        //this.odometer_over_limit = 0;
    }

    public ConditionReport(int condition_report_id, int odometer_over_limit, int contract_id, int damage_level_id,int odometer_price) {
        this.condition_report_id = condition_report_id;
        this.odometer_over_limit = odometer_over_limit;
        this.contract_id = contract_id;
        this.damage_level_id = damage_level_id;
        this.odometer_price=odometer_price;
    }

    public int getCondition_report_id() {
        return condition_report_id;
    }

    public void setCondition_report_id(int condition_report_id) {
        this.condition_report_id = condition_report_id;
    }

    public int getOdometer_over_limit() {
        return odometer_over_limit;
    }

    public void setOdometer_over_limit(int odometer_over_limit) {
        this.odometer_over_limit = odometer_over_limit;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getDamage_level_id() {
        return damage_level_id;
    }

    public void setDamage_level_id(int damage_level_id) {
        this.damage_level_id = damage_level_id;
    }

    public int getOdometer_price()
    {
        return odometer_price;
    }

    public void setOdometer_price(int odometer_price)
    {
        this.odometer_price = odometer_price;
    }
}
