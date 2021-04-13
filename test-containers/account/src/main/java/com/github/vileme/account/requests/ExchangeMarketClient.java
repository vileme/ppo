package com.github.vileme.account.requests;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ExchangeMarketClient {
    final HttpClient client;
    final URI baseUri;

    public ExchangeMarketClient() throws URISyntaxException {
        this.client = HttpClient.newHttpClient();
        this.baseUri = new URI("http://localhost:8080");
    }

    public int get_price(String companyName, int count) throws IOException, InterruptedException {
        URI query_uri = this.baseUri.resolve(String.format("/stock_price?company=%s", companyName));
        HttpRequest req = HttpRequest.newBuilder()
                .uri(query_uri)
                .GET()
                .build();
        HttpResponse<String> resp = this.client.send(req, HttpResponse.BodyHandlers.ofString());
        int price = Integer.parseInt(resp.body());
        return price * count;
    }

    public boolean buy(String companyName, int count) throws IOException, InterruptedException {
        URI query_uri = this.baseUri.resolve(String.format("/buy_stocks?company=%s&amount=%s", companyName, count));
        HttpRequest req = HttpRequest.newBuilder()
                .uri(query_uri)
                .GET()
                .build();
        HttpResponse<String> resp = this.client.send(req, HttpResponse.BodyHandlers.ofString());
        return resp.body().contains("OK");
    }


}
