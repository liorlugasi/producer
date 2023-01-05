package com.memphis.consumer.services.configurations;

import com.memphis.consumer.services.implementations.ConsumerService;
import com.memphis.consumer.services.interfaces.IConsumerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public IConsumerService getConsumer(){return new ConsumerService();}

}
