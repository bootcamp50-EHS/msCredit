package com.bootcamp.ehs.repo;

import com.bootcamp.ehs.model.CardCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICardCreditRepo extends ReactiveMongoRepository<CardCredit, String> {
}
