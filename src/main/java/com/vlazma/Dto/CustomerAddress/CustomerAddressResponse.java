package com.vlazma.Dto.CustomerAddress;

import com.vlazma.Models.Address;
import com.vlazma.Models.Customers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressResponse {
    private Customers customers;
    private Address address;
}
