package com.business.api.routes;

import com.business.api.processors.SuccessProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostsRoute extends RouteBuilder {

    @Autowired
    SuccessProcessor successProcessor;

    @Override
    public void configure() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        from("direct:log.body.route")
                .log("Method body: ${body}")
                    .to("bean:luceneAdapter?method=addObjectToIndex(${in.body})")
                .process(successProcessor)
                .to("file:data/output?fileName=post-${date:now:yyyyMMdd-HHmmss}.txt");
    }
}
