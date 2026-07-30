package com.example.iaasconsole.config;

import com.example.iaasconsole.security.CloudStackAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class CloudStackClientConfig {

    private final CloudStackAuthInterceptor cloudStackAuthInterceptor;

    @Bean
    public RestClient cloudStackRestClient() {
        return RestClient.builder()
                .requestInterceptor(cloudStackAuthInterceptor)
                .build();
    }
}
