package com.springboot.camel.microservice.camelmicroservice.routes;

import com.springboot.camel.microservice.camelmicroservice.components.GetCurrentTimeBean;
import com.springboot.camel.microservice.camelmicroservice.components.SimpleLoggingProcessingComponents;
import com.springboot.camel.microservice.camelmicroservice.components.SimpleLoggingProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
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
        
        final String logBody = "${body}";

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

        /**
         * 
         * 
        from("timer:first-timer")
        .log(logBody)
        .transform().constant("Constant Message")
        .log(logBody)
        .bean(getCurrentTimeBean, "getCurrentTime")
        .log(logBody)
        .bean(simpleLoggingProcessingComponents, "process")
        .log(logBody)
        .to("log:first-timer");
        */
        
        from("timer:first-timer")
        .log(logBody)
        .transform().constant("Constant Message")
        .log(logBody)
        .bean(getCurrentTimeBean, "getCurrentTime")
        .log(logBody)
        .bean(simpleLoggingProcessingComponents, "process")
        .log(logBody)
        .process(new SimpleLoggingProcessor())
        .to("log:first-timer");
        
    }
    
}
