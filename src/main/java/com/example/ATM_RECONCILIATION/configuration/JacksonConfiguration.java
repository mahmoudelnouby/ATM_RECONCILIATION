package com.example.ATM_RECONCILIATION.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.example.ATM_RECONCILIATION.ClobHandler.ClobSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Clob;


@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Clob.class, new ClobSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}


