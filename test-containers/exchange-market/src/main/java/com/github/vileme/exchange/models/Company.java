package com.github.vileme.exchange.models;


public class Company {
    public final String name;
    public int amount_stocks = 0;
    public int owned_stocks = 0;
    public int price = 0;

    public Company(String name) {
        this.name = name;
    }
}
