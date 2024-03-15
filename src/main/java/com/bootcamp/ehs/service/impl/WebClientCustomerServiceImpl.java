package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.DTO.CustomerDTO;
import com.bootcamp.ehs.service.IWebClientCustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientCustomerServiceImpl implements IWebClientCustomerService {

    @Qualifier("customerWebClient")
    private final WebClient customerWebClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientCustomerServiceImpl.class);


    @Override
    public Mono<CustomerDTO> findCustomerById(String id) {
        LOGGER.info("En findCustomerById: el id= "+ id);

        return customerWebClient.get()
                .uri("/api/customer/list/{id}", id)
                .retrieve()
                .bodyToMono(CustomerDTO.class);
    }
}
