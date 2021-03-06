package com.springboot.camel.app.route;

import com.springboot.camel.app.alert.MailProcessor;
import com.springboot.camel.app.domain.Item;
import com.springboot.camel.app.exceptions.DataException;
import com.springboot.camel.app.process.BuildSqlProcessor;
import com.springboot.camel.app.process.SuccessProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class SimpleCamelRoute extends RouteBuilder{

    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    BuildSqlProcessor buildSqlProcessor;

    @Autowired
    SuccessProcessor successProcessor;

    @Autowired
    MailProcessor mailProcessor;

    @Override
    public void configure() throws Exception {
        log.info(" ========>>>> Starting the Camel Route: SimpleCamelRoute");

        DataFormat bindy = new BindyCsvDataFormat(Item.class);

        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR));

        onException(PSQLException.class).log(LoggingLevel.ERROR, "PSQLException in the route ${body}")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class)
                .log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);

        from("{{startRoute}}").routeId("mainRoute") // timer:hello?period=10s
                .log("Timer Invoked and the body is: " + environment.getProperty("message"))
                .choice()
                    .when(header("env").isNotEqualTo("mock"))
                        .pollEnrich("{{fromRoute}}") // file:data/input?delete=true&readLock=none
                    .otherwise()
                        .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}") // file:data/output
                    .unmarshal(bindy)
                    .log("The unmarshaled object is ${body}")
                    .split(body())
                        .log("Record is ${body}")
                        .process(buildSqlProcessor)
                        .to("{{toRoute2}}") // jdbc:dataSource
                .end()
        .process(successProcessor)
        .to("{{toRoute3}}"); // file:data/output?fileName=success.txt

        log.info(" =======>>>>> Ending the Camel Route");
    }
}
