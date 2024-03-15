package com.bootcamp.ehs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String docType;
    private String docNumber;
    private String address;
    private String phone;
    private String firstName;
    private String lastName;
    private String civilStatus;
    private String businessName;
    private String representative;
    private String typeCustomer;
    private Boolean status;

}
