package com.example.blogservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class SessionConfiguration {

    @Value("${expire.in}")
    Long expirationPeriod;
}
