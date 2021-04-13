package com.github.vileme.account.dao;

import org.springframework.stereotype.Component;
import com.github.vileme.account.models.Account;

import java.util.HashMap;

@Component
public class InMemoryDao implements Dao {
    private final HashMap<String, Account> db;

    public InMemoryDao() {
        this.db = new HashMap<>();
    }

    @Override
    public boolean createUser(String userName) {
        if (!this.db.containsKey(userName)) {
            this.db.put(userName, new Account(userName));
            return true;
        }
        return false;
    }


    @Override
    public boolean addCash(String userName, int amount) {
        if (!this.db.containsKey(userName)) {
            return false;
        }
        this.db.get(userName).cash += amount;
        return true;
    }


    @Override
    public Account getAccount(String userName) {
        return this.db.get(userName);
    }


    @Override
    public boolean addStocks(String userName, String companyName, int count) {
        if (!this.db.containsKey(userName)) {
            return false;
        }
        Account acc = this.db.get(userName);
        acc.stocks.put(companyName, count);
        return true;
    }
}
