package com.springboot.camel.app.alert;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailProcessor implements Processor {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    Environment environment;

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        log.info(" ======>>>>>> Exception caught in mail processor: " + e.getMessage());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(environment.getProperty("mailTo"));
        message.setSubject("Exception in Camel Route");
        message.setText("Exception happened in the camel Route: " + e.getMessage());

        emailSender.send(message);
    }
}

