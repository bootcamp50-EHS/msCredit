package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.DTO.BankDTO;
import com.bootcamp.ehs.service.IBankCreditService;
import com.bootcamp.ehs.service.IWebClientBankService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientBankServiceImpl implements IWebClientBankService {

    @Qualifier("bankWebClient")
    private final WebClient bankWebClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientBankServiceImpl.class);
    @Override
    public Mono<BankDTO> findBankByCode(String codeBank) {
        return bankWebClient.get()
                .uri("/list/code/{codeBank}", codeBank)
                .retrieve()
                .bodyToMono(BankDTO.class);
    }
}
