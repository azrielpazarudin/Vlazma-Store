package com.vlazma.Dto.Orders;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRequest {
    
    @Size(min=1,max=4)
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    private String customerId;
    @NotNull
    private String destination;
    @NotEmpty
    private String courierName;
    private int total_price;
}
