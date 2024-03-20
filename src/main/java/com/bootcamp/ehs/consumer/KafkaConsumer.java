package com.bootcamp.ehs.consumer;

import com.bootcamp.ehs.model.BankCredit;
import com.bootcamp.ehs.repo.IBankCreditRepo;
import com.bootcamp.ehs.service.IBankCreditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper;

    @Autowired
    private IBankCreditService bankCreditService;

    @Autowired
    private IBankCreditRepo bankCreditRepo;

    @Autowired
    public KafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payCredit-topic" , groupId = "default")
    public void payCredit(String message) throws JsonProcessingException {
        BankCredit bankCredit = objectMapper.readValue(message, BankCredit.class);
        LOGGER.info("Obteniendo BankCredit de Consumer: {} " , bankCredit);
        bankCreditRepo.save(bankCredit)
                .subscribe(credit -> {
            LOGGER.info("KafkaConsumer ->  Deuda de Credito ha sido actualizada" + credit.getId());
        });

    }
}
