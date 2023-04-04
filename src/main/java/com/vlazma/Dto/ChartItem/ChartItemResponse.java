package com.vlazma.Dto.ChartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartItemResponse {
    private int chartId;
    private int productId;
    private String productName;
    private int productPrice;
    private int quantity;
}
