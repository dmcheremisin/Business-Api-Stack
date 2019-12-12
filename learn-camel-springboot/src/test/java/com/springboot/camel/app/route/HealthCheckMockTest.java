package com.springboot.camel.app.route;

import com.springboot.camel.app.process.HealthCheckProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HealthCheckMockTest extends CamelTestSupport {

    @Autowired
    CamelContext context;

    @Autowired
    Environment environment;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    ProducerTemplate producerTemplate;


    @Override
    public RouteBuilder createRouteBuilder() {
        return new HealthCheckRoute();
    }

    @Autowired
    HealthCheckProcessor healthCheckProcessor;

    @Before
    public void setUp() {

    }

    @Test
    public void healthRoutTest() {
        String input = "{" +
                "\"status\":\"DOWN\",\"camel\":{\"status\":\"UP\",\"name\":\"camel-1\",\"version\":\"2.20.1\",\"contextStatus\":\"Started\"}," +
                "\"camel-health-checks\":{\"status\":\"UP\",\"route:healthRoute\":\"UP\",\"route:mainRoute\":\"UP\"}," +
                "\"mail\":{\"status\":\"DOWN\",\"location\":\"smtp.gmail.com:587\",\"error\":\"com.sun.mail.util.MailConnectException: Couldn't connect to host, port: smtp.gmail.com, 587; timeout -1\"}," +
                "\"diskSpace\":{\"status\":\"UP\",\"total\":249533820928,\"free\":12431986688,\"threshold\":10485760}," +
                "\"db\":{\"status\":\"UP\",\"database\":\"PostgreSQL\",\"hello\":1}}";
        String response = (String) producerTemplate.requestBodyAndHeader(
                environment.getProperty("healthRoute"), input, "env", environment.getProperty("spring.profiles.active"));

        System.out.println("Response: " + response);

        String expectedMessage = "\n >>>>>!!!!!!<<<< STATUS component in the route is down" +
                "\n >>>>>!!!!!!<<<< MAIL component in the route is down";
        assertEquals(expectedMessage, response);

    }
}
