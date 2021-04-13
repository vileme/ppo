package com.github.vileme.account.requests;

import com.github.vileme.account.dao.Dao;
import org.springframework.stereotype.Component;
import com.github.vileme.account.models.Account;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class Handlers {
    final Dao db;
    final ExchangeMarketClient exchange;

    public Handlers(Dao db, ExchangeMarketClient exchange) {
        this.exchange = exchange;
        System.out.println("Handlers initializing...");
        this.db = db;
    }

    public String doAddAccount(String userName) {
        if (!this.db.createUser(userName)) {
            return "FAIL";
        }
        return "OK";
    }

    public String doAddCash(String userName, int amount) {
        if (!this.db.addCash(userName, amount)) {
            return "FAIL";
        }
        return "OK";
    }

    public String doInfo(String userName) {
        Account acc = this.db.getAccount(userName);
        if (acc == null) {
            return "Unknown user";
        }
        return String.format("User: %s\n", acc.name) +
                String.format("Cash: %s\n", acc.cash) +
                "Stocks:\n" +
                acc.stocks.entrySet()
                        .stream()
                        .map(e -> String.format("  company: %s, count: %s\n", e.getKey(), e.getValue()))
                        .collect(Collectors.joining());
    }

    public String doBuy(String userName, String companyName, int count) throws IOException, InterruptedException {
        Account acc = this.db.getAccount(userName);
        int neededCash = this.exchange.get_price(companyName, count);
        if (neededCash > acc.cash) {
            return "FAIL";
        }
        if (!this.exchange.buy(companyName, count)) {
            return "FAIL";
        }
        if (this.db.addStocks(userName, companyName, count)) {
            System.out.println("PANIC: partial fail");
            return "FAIL";
        }
        if (this.db.addStocks(userName, companyName, count)) {
            System.out.println("PANIC: partial fail");
            return "FAIL";
        }
        return "OK";
    }

}
