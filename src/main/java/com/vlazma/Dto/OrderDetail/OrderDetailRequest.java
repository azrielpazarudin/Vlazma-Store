package com.vlazma.Dto.OrderDetail;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    @Size(min=1,max=3)
    private String orderId;
    @Size(min=1,max=3)
    private String chartId;
    @Size(min=1,max=7)
    private String shipCost;
}
