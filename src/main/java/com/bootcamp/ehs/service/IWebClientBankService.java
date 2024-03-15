package com.bootcamp.ehs.service;

import com.bootcamp.ehs.DTO.BankDTO;
import reactor.core.publisher.Mono;

public interface IWebClientBankService {

    Mono<BankDTO> findBankByCode(String codeBank);
}
