package com.example.bilabonnement.model;

public class DamageLevel
{
    private int damage_level_id;
    private int damage_level;
    private int damage_price;

    public DamageLevel(){

    }

    public DamageLevel(int damage_level_id, int damage_level, int damage_price)
    {
        this.damage_level_id = damage_level_id;
        this.damage_level = damage_level;
        this.damage_price = damage_price;
    }

    public int getDamage_level_id()
    {
        return damage_level_id;
    }

    public void setDamage_level_id(int damage_level_id)
    {
        this.damage_level_id = damage_level_id;
    }

    public int getDamage_level()
    {
        return damage_level;
    }

    public void setDamage_level(int damage_level)
    {
        this.damage_level = damage_level;
    }

    public int getDamage_price()
    {
        return damage_price;
    }

    public void setDamage_price(int damage_price)
    {
        this.damage_price = damage_price;
    }
}
