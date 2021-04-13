package com.github.vileme.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.vileme.account.requests.Handlers;

import java.io.IOException;


@RestController
public class Controller {
    final Handlers handlers;

    public Controller(Handlers handlers) {
        System.out.println("Controller initializing...");
        this.handlers = handlers;
    }

    @RequestMapping("/add_account")
    public String addAccount(String name) {
        return this.handlers.doAddAccount(name);
    }

    @RequestMapping("/add_cash")
    public String addCash(String name, int amount) {
        return this.handlers.doAddCash(name, amount);
    }

    @RequestMapping("/info")
    public String info(String name) {
        return this.handlers.doInfo(name);
    }

    @RequestMapping("/buy")
    public String buy(String name, String company, int count) throws IOException, InterruptedException {
        return this.handlers.doBuy(name, company, count);
    }

}
