package com.springboot.apache.camel.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentTimeService {

    @Autowired
    private Environment env;

    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return env.getProperty("message") + " " + now.toString();
    }
}
