package com.springboot.camel.microservice.camelmicroservice.routes;

import java.time.LocalDateTime;

import com.springboot.camel.microservice.camelmicroservice.components.GetCurrentTimeBean;
import com.springboot.camel.microservice.camelmicroservice.components.SimpleLoggingProcessingComponents;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimerRouter extends RouteBuilder {

    private final GetCurrentTimeBean getCurrentTimeBean;
    private final SimpleLoggingProcessingComponents simpleLoggingProcessingComponents;

    @Autowired
    public TimerRouter(GetCurrentTimeBean getCurrentTimeBean, SimpleLoggingProcessingComponents simpleLoggingProcessingComponents){
        this.getCurrentTimeBean = getCurrentTimeBean;
        this.simpleLoggingProcessingComponents = simpleLoggingProcessingComponents;
    }

    @Override
    public void configure() throws Exception {
        // Sequence
        // listen to a queue    - endpoint 1
        // do transformation on message
        // save it to database  - endpoint 2

        // Two types of operations that we can do in specific rout
        // 1. Processing - When some process dosen't make change in the body of they message it self
        // 2. Transformation - If you doing something that changes the body of message
        
        /**
         * 
         * Constant data tranformation
        from("timer:first-timer")
        .transform().constant("Constant Message")
        .transform().constant("Time Now : " + LocalDateTime.now())
        .to("log:first-timer");
                 */

        /**
         * 
         * Dynamic Data change 
        from("timer:first-timer")
        //.bean(getCurrentTimeBean)
        .bean(getCurrentTimeBean,"getCurrentTime")
        .to("log:first-timer");
        */

        /**
         * 
         * Adding log
        final String logBody = "${body}";
        from("timer:first-timer")
        .log(logBody)
        .transform().constant("Constant Message")
        .log(logBody)
        .bean(getCurrentTimeBean, "getCurrentTime")
        .log(logBody)
        .to("log:first-timer");
        */

        final String logBody = "${body}";
        from("timer:first-timer")
        .log(logBody)
        .transform().constant("Constant Message")
        .log(logBody)
        .bean(getCurrentTimeBean, "getCurrentTime")
        .log(logBody)
        .bean(simpleLoggingProcessingComponents, "process")
        .log(logBody)
        .to("log:first-timer");
        
        /**
         * 
         * Time now is 2021-02-02T15:08:56.314134]
2021-02-02 15:08:57.313  INFO 7468 --- [r://first-timer] route9                                   : null
2021-02-02 15:08:57.313  INFO 7468 --- [r://first-timer] route9                                   : Constant Message
2021-02-02 15:08:57.313  INFO 7468 --- [r://first-timer] route9                                   : Time now is 2021-02-02T15:08:57.313886
2021-02-02 15:08:57.313  INFO 7468 --- [r://first-timer] .m.c.c.SimpleLoggingProcessingComponents : SimpleLogginProcessingComponents {}Time now is 2021-02-02T15:08:57.313886
2021-02-02 15:08:57.314  INFO 7468 --- [r://first-timer] route9                                   : Time now is 2021-02-02T15:08:57.313886
2021-02-02 15:08:57.314  INFO 7468 --- [r://first-timer] first-timer                              : Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is 2021-02-02T15:08:57.313886]
2021-02-02 15:08:58.313  INFO 7468 --- [r://first-timer] route9                                   : null
2021-02-02 15:08:58.313  INFO 7468 --- [r://first-timer] route9                                   : Constant Message
2021-02-02 15:08:58.313  INFO 7468 --- [r://first-timer] route9                                   : Time now is 2021-02-02T15:08:58.313721
2021-02-02 15:08:58.313  INFO 7468 --- [r://first-timer] .m.c.c.SimpleLoggingProcessingComponents : SimpleLogginProcessingComponents {}Time now is 2021-02-02T15:08:58.313721
2021-02-02 15:08:58.314  INFO 7468 --- [r://first-timer] route9                                   : Time now is 2021-02-02T15:08:58.313721
2021-02-02 15:08:58.314  INFO 7468 --- [r://first-timer] first-timer                              : Exchange[ExchangePattern: InOnly, BodyType: Stri
         * 
        */
    }
    
}
