package com.vlazma.Dto.CustomerAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressRequest {
    private int customerId;
    private int addressId;
}
