package com.springboot.apache.camel.app.controller;

import com.springboot.apache.camel.app.service.CurrentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private CurrentTimeService currentTimeService;

    @RequestMapping("/currentDateTime")
    public String getCurrentDateTime(){
        return currentTimeService.getCurrentDateTime();
    }
}
