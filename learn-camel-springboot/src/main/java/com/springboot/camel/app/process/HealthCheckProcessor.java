package com.springboot.camel.app.process;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class HealthCheckProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String healthCheckResult = exchange.getIn().getBody(String.class);
        log.info("Health Check String of the Api is : " + healthCheckResult);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(healthCheckResult, new TypeReference<Map<String,Object>>(){});

        StringBuilder sb = new StringBuilder();

        log.info("Map read is: " + map);
        for (String key : map.keySet()) {
            if(map.get(key).toString().contains("DOWN")) {
                sb.append("\n");
                sb.append(" >>>>>!!!!!!<<<< ").append(key.toUpperCase()).append(" component in the route is down");
            }
        }
        if(sb.length() != 0) {
            log.error("Exception message is: " + sb.toString());
            exchange.getIn().setHeader("error", true);
            exchange.getIn().setBody(sb.toString());
            exchange.setProperty(Exchange.EXCEPTION_CAUGHT, sb.toString());
        }

    }
}
