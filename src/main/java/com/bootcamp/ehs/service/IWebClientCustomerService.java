package com.bootcamp.ehs.service;

import com.bootcamp.ehs.DTO.CustomerDTO;
import reactor.core.publisher.Mono;

public interface IWebClientCustomerService {

    Mono<CustomerDTO> findCustomerById(String id);
}
