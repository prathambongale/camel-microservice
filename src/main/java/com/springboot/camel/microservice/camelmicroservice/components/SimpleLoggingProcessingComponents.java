package com.springboot.camel.microservice.camelmicroservice.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleLoggingProcessingComponents {
    
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponents.class);
    
    public void process(String message) {
        logger.info("SimpleLogginProcessingComponents {}" + message);
    }
}
