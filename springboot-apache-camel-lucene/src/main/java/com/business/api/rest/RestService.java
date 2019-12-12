package com.business.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    private HttpEntity getRequestEntity() {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(headers);
    }

    private <T> HttpEntity<T> getRequestEntity(T body) {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(body, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public <T> T get(String url, ParameterizedTypeReference<T> response) {
        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), response);
        return exchange.getBody();
    }

    public <T> T get(String url, Class<T> response) {
        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), response);
        return exchange.getBody();
    }

    public <T> T post(String url, Object body, Class<T> response) {
        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.POST, getRequestEntity(body), response);
        return exchange.getBody();
    }
}
