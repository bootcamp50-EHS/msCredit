package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.model.BankCredit;
import com.bootcamp.ehs.repo.IBankCreditRepo;
import com.bootcamp.ehs.service.IBankCreditService;
import com.bootcamp.ehs.service.IWebClientBankService;
import com.bootcamp.ehs.service.IWebClientCustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankCreditServiceImpl implements IBankCreditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankCreditServiceImpl.class);

    private final IWebClientBankService bankService;
    private final IWebClientCustomerService customerService;
    private final IBankCreditRepo bankCreditRepo;

    @Override
    public Mono<BankCredit> saveCredit(BankCredit bankCredit, String codeBank) {
        LOGGER.info("Procesando registro de Cuenta Bancaria");
        return bankService.findBankByCode(codeBank)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Banco no existe")))
                .flatMap(bankExist -> {
                    bankCredit.setBank(bankExist);
                    return customerService.findCustomerById(bankCredit.getCustomerId())
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Cliente no existe")))
                            .flatMap(customer ->
                                    customer.getTypeCustomer().equals("Empresarial") ? saveBusinessCredit(bankCredit) :
                                            customer.getTypeCustomer().equals("Personal") ? savePersonalCredit(bankCredit) :
                                                    Mono.error(new IllegalArgumentException("Tipo no Soportado"))
                            );

                });
    }


    private Mono<BankCredit> saveBusinessCredit(BankCredit bankCredit) {
        LOGGER.info("Procéso de Crédito Empresarial");
        bankCredit.setCreditType("Empresarial");
        return bankCreditRepo.save(bankCredit);

    }

    private Mono<BankCredit> savePersonalCredit(BankCredit bankCredit) {
        LOGGER.info("Proceso de Crédito Personal");
        bankCredit.setCreditType("Personal");

        return bankCreditRepo.existsByCustomerIdAndBank(bankCredit.getCustomerId(),  bankCredit.getBank())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cliente ya cuenta con 1 crédito en este Banco")))
                .then(bankCreditRepo.save(bankCredit))
                .doOnNext(account -> LOGGER.info("Grabando Crédito Personal"));

        /*return bankCreditRepo.existsByCustomerIdAndBank(bankCredit.getCustomerId(),  bankCredit.getBank())
                .flatMap(exists -> exists
                        ? Mono.<BankCredit>error(new IllegalArgumentException("Cliente ya cuenta con 1 crédito en este Banco"))
                        : bankCreditRepo.save(bankCredit)
                        .doOnNext(account -> LOGGER.info("Grabando Crédito Personal"))
                );*/

    }

    // Metodo que actualiza el credito bancario
    @Override
    public Mono<BankCredit> updateCredit(BankCredit bankCredit) {
        LOGGER.info("en BankCreditServiceImpl: {}", bankCredit);
        return bankCreditRepo.findById(bankCredit.getId())
                .flatMap(accountExist -> {
                    accountExist.setPayment(bankCredit.getPayment());
                    LOGGER.info("Credit -> UpdateCredit : Actualiza Cuentabancaria: ", accountExist);
                    return bankCreditRepo.save(accountExist);
                });
    }

    // Metodo para obteber el credito con el id.
    @Override
    public Mono<BankCredit> getCreditById(String id) {
        LOGGER.info("Credit Service -> getCreditById : ID: "+ id);
        return bankCreditRepo.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Credito Bancario no encontrado")));
    }

    @Override
    public Flux<BankCredit> getCreditByCustomer(String customerId) {
        return bankCreditRepo.findByCustomerId(customerId);
    }

}
