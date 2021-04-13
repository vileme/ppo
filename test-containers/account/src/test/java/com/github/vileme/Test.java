package com.github.vileme;

import org.junit.Assert;
import org.junit.ClassRule;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import com.github.vileme.account.dao.InMemoryDao;
import com.github.vileme.account.requests.ExchangeMarketClient;
import com.github.vileme.account.requests.Handlers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Test {
    @ClassRule
    public static GenericContainer simpleWebServer
            = new FixedHostPortGenericContainer("exchange-market:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    public static ExchangeMarketClient exchange;

    private static void init_market(String company, int amount) throws Exception {
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/add_company?name=" + company))
                .GET()
                .build();
        HttpResponse<String> response1 = HttpClient.newHttpClient().send(request1, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals("OK", response1.body());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(String.format("http://localhost:8080/add_stocks?company=%s&amount=%s", company, amount)))
                .GET()
                .build();
        HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals("OK", response2.body());

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(new URI(String.format("http://localhost:8080/reset_stock_price?company=%s&price=%s", company, 1)))
                .GET()
                .build();
        HttpResponse<String> response3 = HttpClient.newHttpClient().send(request3, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals("OK", response3.body());
    }


    @org.junit.Test
    public void test_test() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/add_company?name=foo"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals("OK", response.body());
    }


    @org.junit.Test
    public void test_buy() throws Exception {
        init_market("Google", 10);
        Handlers handlers = new Handlers(new InMemoryDao(), exchange);
        String resp1 = handlers.doAddAccount("Vlad");
        Assert.assertEquals("OK", resp1);

        String resp2 = handlers.doAddCash("Vlad", 100);
        Assert.assertEquals("OK", resp2);

        String resp3 = handlers.doBuy("Vlad", "spacex", 10);
        Assert.assertEquals("OK", resp3);

        String info = handlers.doInfo("Vlad");
        Assert.assertEquals("User: Vlad\nCash: 90\nStocks:\n  company: Google, count: 10\n", info);
    }


    @org.junit.Test
    public void test_sale_too_many() throws Exception {
        init_market("ITMO", 10);
        Handlers handlers = new Handlers(new InMemoryDao(), exchange);
        String resp1 = handlers.doAddAccount("Vlad");
        Assert.assertEquals("Vlad", resp1);

        String resp2 = handlers.doAddCash("Vlad", 100);
        Assert.assertEquals("OK", resp2);

    }


    @org.junit.Test
    public void test_too_expensive() throws Exception {
        init_market("Apple", 15);
        Handlers handlers = new Handlers(new InMemoryDao(), exchange);
        String resp1 = handlers.doAddAccount("Vlad");
        Assert.assertEquals("OK", resp1);

        String resp3 = handlers.doBuy("Vlad", "Apple", 15);
        Assert.assertEquals("FAIL", resp3);
    }
}
