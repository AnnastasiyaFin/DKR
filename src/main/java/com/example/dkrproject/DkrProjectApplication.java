package com.example.dkrproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EntityScan
@SpringBootApplication
public class DkrProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DkrProjectApplication.class, args);
    }

    @Bean("publisher")
    public ModelMapper getPublisherMapper() {
        ModelMapper publisherMapper = new ModelMapper();
        publisherMapper.getConfiguration().setSkipNullEnabled(true);
        return publisherMapper;
    };

}
