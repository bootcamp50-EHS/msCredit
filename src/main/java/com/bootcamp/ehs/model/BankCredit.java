package com.bootcamp.ehs.model;

import com.bootcamp.ehs.DTO.BankDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CustomerCredit")
public class BankCredit {

    @Id
    private String id;
    private BankDTO bank;
    private String customerId;
    private String creditType;
    private BigDecimal amount;
    @Builder.Default
    private BigDecimal payment = BigDecimal.valueOf(0);

}
