package com.vlazma.Dto.Address;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotEmpty(message = "Province Is Required")
    private String province;
    @NotEmpty(message = "City Is Required")
    private String city;
    @NotEmpty(message = "Complete Address Is Required")
    private String completeAddress;
}
