package com.bootcamp.ehs.repo;

import com.bootcamp.ehs.DTO.BankDTO;
import com.bootcamp.ehs.model.BankCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankCreditRepo extends ReactiveMongoRepository<BankCredit, String> {

    Mono<Boolean> existsByCustomerIdAndBank (String customerId, BankDTO bank);

    Flux<BankCredit> findByCustomerId(String customerId);
}
