package com.vlazma.Dto.ChartItem;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartItemRequest {
    @NotEmpty
    private String chartId;
    @NotEmpty
    private String productId;
    @NotEmpty
    private String quantity;
}
