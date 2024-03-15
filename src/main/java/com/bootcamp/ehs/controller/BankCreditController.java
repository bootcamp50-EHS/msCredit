package com.bootcamp.ehs.controller;

import com.bootcamp.ehs.model.BankCredit;
import com.bootcamp.ehs.service.IBankCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit")
public class BankCreditController {

    private final IBankCreditService bankCreditService;

    // Metodo para crear el credito bancario en un banco
    @PostMapping("/create/withbank/{codeBank}")
    public Mono<ResponseEntity<BankCredit>> crearCreditoBancario(@PathVariable("codeBank") String codeBank, @RequestBody BankCredit bankCredit){
        return bankCreditService.saveCredit(bankCredit, codeBank)
                            .map(ResponseEntity::ok)
                            .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PutMapping("/update")
    public Mono<ResponseEntity<BankCredit>> actualizarCreditoBancario(@RequestBody BankCredit bankCredit){
        return bankCreditService.updateCredit(bankCredit)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Metodo para obteber los creditos bancarios de un cliente
    @GetMapping("/retrieve/bycustomer/{customerId}")
    public Mono<ResponseEntity<List<BankCredit>>> retrieveBankCreditsByCustomer (@PathVariable String customerId){
        return bankCreditService.getCreditByCustomer(customerId)
                .collectList()
                .flatMap(credits -> credits.isEmpty() ?
                        Mono.just(ResponseEntity.notFound().build()) :
                        Mono.just(ResponseEntity.ok(credits)));

    }

    // Metodo para obtener un credito bancario segun su Id.
    @GetMapping("/retrieve/{creditId}")
    public Mono<ResponseEntity<BankCredit>> retrieveBankCreditById(@PathVariable("creditId") String creditId){
        return bankCreditService.getCreditById(creditId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
