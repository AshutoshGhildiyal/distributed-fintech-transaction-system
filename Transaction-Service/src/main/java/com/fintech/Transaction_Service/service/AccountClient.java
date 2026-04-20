package com.fintech.Transaction_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.http.HttpClient;

@Service
public class AccountClient {

    @Autowired
    private  RestTemplate restTemplate;

    private final String ACCOUNT_SERVICE_URL = "http://localhost:8081/accounts";

    public void debit(Long accountId, BigDecimal amount, String idempotencyKey ){
        String url = ACCOUNT_SERVICE_URL + "/debit?accountId=" + accountId + "&amount=" + amount;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Idempotency-Key", idempotencyKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.POST,entity,String.class);


    }

    public void credit(Long accountId, BigDecimal amount, String idempotencyKey) {
        String url = ACCOUNT_SERVICE_URL + "/credit?accountId=" + accountId + "&amount=" + amount;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Idempotency-Key", idempotencyKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
