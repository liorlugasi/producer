package com.memphis.producer.services.configurations;

import com.memphis.producer.services.implementations.ProducerService;
import com.memphis.producer.services.interfaces.IProducerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public IProducerService getProducer(){return new ProducerService();}

}
