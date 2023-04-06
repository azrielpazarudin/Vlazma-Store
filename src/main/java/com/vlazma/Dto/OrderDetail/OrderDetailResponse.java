package com.vlazma.Dto.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private int orderId;
    private int chartId;
    private int orderTotal;
    private int shipCost;
}
