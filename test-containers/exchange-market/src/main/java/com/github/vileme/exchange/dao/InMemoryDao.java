package com.github.vileme.exchange.dao;

import com.github.vileme.exchange.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InMemoryDao implements Dao {
    private final HashMap<String, Company> stocks;

    @Autowired
    public InMemoryDao() {
        this.stocks = new HashMap<>();
        System.out.println("In memory dao initializing...");
    }

    @Override
    public boolean addCompany(Company company) {
        System.out.println(String.format("Add company with name %s", company.name));
        if (this.stocks.containsKey(company.name)) {
            System.out.println("Unknown company" + company.name);
            return false;
        }
        this.stocks.put(company.name, company);
        return true;
    }

    @Override
    public boolean addStocks(String companyName, int amount) {
        if (!this.stocks.containsKey(companyName)) {
            System.out.println("Unknown company" + companyName);
            return false;
        }
        Company company = this.stocks.get(companyName);
        System.out.println(String.format("Add stocks %s for company '%s'", amount, company.name));
        company.amount_stocks += amount;
        return true;
    }

    @Override
    public int getStockPrice(String companyName) {
        if (!this.stocks.containsKey(companyName)) {
            System.out.println("Unknown company" + companyName);
            return -1;
        }
        return this.stocks.get(companyName).price;
    }

    @Override
    public int getAmountStocks(String companyName) {
        if (!this.stocks.containsKey(companyName)) {
            System.out.println("Unknown company" + companyName);
            return -1;
        }
        return this.stocks.get(companyName).amount_stocks;
    }

    @Override
    public boolean buyStocks(String companyName, int amount) {
        if (!this.stocks.containsKey(companyName)) {
            System.out.println("Unknown company" + companyName);
            return false;
        }
        Company company = this.stocks.get(companyName);
        if (company.amount_stocks < amount) {
            System.out.println("Not enough stocks");
            return false;
        }
        company.amount_stocks -= amount;
        company.owned_stocks += amount;
        return true;
    }

}
