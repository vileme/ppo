package com.github.vileme.exchange.dao;

import com.github.vileme.exchange.models.Company;

public interface Dao {

    boolean addCompany(Company company);

    boolean addStocks(String companyName, int amount);

    int getStockPrice(String companyName);

    int getAmountStocks(String companyName);

    boolean buyStocks(String companyName, int amount);


}
