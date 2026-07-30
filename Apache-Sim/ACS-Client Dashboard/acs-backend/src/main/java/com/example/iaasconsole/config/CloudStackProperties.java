package com.example.iaasconsole.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.cloudstack")
@Data
public class CloudStackProperties {
    private String baseUrl;
    private String apiKey;
    private String secretKey;
}
