package com.github.vileme.account.dao;

import com.github.vileme.account.models.Account;

public interface Dao {

    boolean createUser(String userName);


    boolean addCash(String userName, int amount);


    Account getAccount(String userName);


    boolean addStocks(String userName, String companyName, int count);
}
