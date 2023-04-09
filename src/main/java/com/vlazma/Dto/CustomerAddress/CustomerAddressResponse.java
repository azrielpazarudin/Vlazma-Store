package com.vlazma.Dto.CustomerAddress;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressResponse {
    private int customerId;
    private String customerName;
    private int idAddress;
    private String province;
    private String city;
    private String completeAddress; 
}
