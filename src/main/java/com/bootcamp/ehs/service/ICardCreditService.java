package com.bootcamp.ehs.service;

import com.bootcamp.ehs.model.BankCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICardCreditService {

    Mono<BankCredit> saveCredit(BankCredit bankCredit);

    Mono<BankCredit> updateCredit(BankCredit bankCredit);

    Mono<BankCredit> getCreditById(String id);

    Flux<BankCredit> getCreditByCustomer(String customerId);

    Flux<BankCredit> getCredit();
}
