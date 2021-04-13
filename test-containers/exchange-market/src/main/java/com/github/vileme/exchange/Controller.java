package com.github.vileme.exchange;

import com.github.vileme.exchange.dao.Dao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.vileme.exchange.models.Company;

@RestController
public class Controller {
    private final Dao db;

    public Controller(Dao dao) {
        this.db = dao;
    }

    @RequestMapping("/add_company")
    public void addCompany(String name) {
        if (this.db.addCompany(new Company(name))) {
            System.out.println("adding company...");
        }
        System.out.println("can't add company");
    }

    @RequestMapping("/add_stocks")
    public void addStocks(String company, int amount) {
        if (this.db.addStocks(company, amount)) {
            System.out.println("adding stocks...");
        }
        System.out.println("can't add stocks");
    }

    @RequestMapping("/stock_price")
    public String getStockPrice(String company) {
        int price = this.db.getStockPrice(company);
        return String.valueOf(price);
    }

    @RequestMapping("/amount_stocks")
    public String getAmountStocks(String company) {
        int cnt = this.db.getAmountStocks(company);
        return String.valueOf(cnt);
    }

    @RequestMapping("/buy_stocks")
    public void buyStocks(String company, int amount) {
        if (this.db.buyStocks(company, amount)) {
            System.out.println("buying stocks");
        }
        System.out.println("can't buy stocks");
    }

}
