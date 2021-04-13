package com.github.vileme.account.models;

import java.util.HashMap;

public class Account {
    public final String name;
    public int cash;
    public HashMap<String, Integer> stocks;

    public Account(String name) {
        this.name = name;
        this.cash = 0;
        this.stocks = new HashMap<>();
    }
}