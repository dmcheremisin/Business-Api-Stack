package com.springboot.camel.app.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by Dmitrii on 29.03.2019.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleCamelMockTest extends CamelTestSupport {

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

    @Test
    public void testMoveFile() throws InterruptedException {

        String message = "type,sku#,itemdescription,price\n" +
                "ADD,100,Samsung TV,500\n" +
                "ADD,101,LG TV,500";
        MockEndpoint mockEndpoint = getMockEndpoint(environment.getProperty("toRoute1"));
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(message);

        producerTemplate.sendBodyAndHeader(environment.getProperty("startRoute"),
                                            message, "env", environment.getProperty("spring.profiles.active"));
        assertMockEndpointsSatisfied();

    }

    @Test
    public void testMoveFileMockAndDb() throws InterruptedException {

        String message = "type,sku#,itemdescription,price\n" +
                "ADD,100,Samsung TV,500\n" +
                "ADD,101,LG TV,500";

        String outputMessage = "Data updated successfully";

        MockEndpoint mockEndpoint1 = getMockEndpoint(environment.getProperty("toRoute1"));
        mockEndpoint1.expectedMessageCount(1);
        mockEndpoint1.expectedBodiesReceived(message);

        MockEndpoint mockEndpoint3 = getMockEndpoint(environment.getProperty("toRoute3"));
        mockEndpoint3.expectedMessageCount(1);
        mockEndpoint3.expectedBodiesReceived(outputMessage);

        producerTemplate.sendBodyAndHeader(environment.getProperty("startRoute"),
                                            message, "env", environment.getProperty("spring.profiles.active"));
        assertMockEndpointsSatisfied();

    }
}
