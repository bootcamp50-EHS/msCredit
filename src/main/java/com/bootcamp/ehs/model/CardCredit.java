package com.bootcamp.ehs.model;

import com.bootcamp.ehs.DTO.BankDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCredit {

    @Id
    private String id;
    private BankDTO bank;
    private String customerId;
    private String cardType;
    private String cardNumber;
    private BigDecimal amount;
    private BigDecimal debit;
    private String  cardHolder;

}
