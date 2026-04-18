package com.backend_torch.Data.Persistence.API.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 1. Support for Java 8 Date/Time (Instant, LocalDate, etc.)
        mapper.registerModule(new JavaTimeModule());

        // 2. Write dates as ISO-8601 strings instead of timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 3. Don't crash if the Bank API sends a new field we didn't define
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 4. Pretty print JSON in logs (Optional, useful for debugging)
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }
}