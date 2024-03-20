package com.bootcamp.ehs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${gateway.service.url}")
    private String gatewayServiceUrl;

    @Value("${customers.service.url}")
    private String customerServiceUrl;

    @Value("${banks.service.url}")
    private String bankServiceUrl;

    @Bean
    public WebClient customerWebClient() {
        return WebClient.builder()
                .baseUrl(customerServiceUrl)
                .build();
    }

    @Bean
    public WebClient bankWebClient(){
        return WebClient.builder()
                .baseUrl(bankServiceUrl)
                .build();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(gatewayServiceUrl)
                .build();
    }
}
