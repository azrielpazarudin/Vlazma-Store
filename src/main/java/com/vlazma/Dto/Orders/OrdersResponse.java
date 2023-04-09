package com.vlazma.Dto.Orders;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private int id;
    private String customerName;
    private LocalDateTime orderDate;
    private String origin;
    private String destination;
    private String courierName;
    private int total_price;
    private String orderStatus;
}
