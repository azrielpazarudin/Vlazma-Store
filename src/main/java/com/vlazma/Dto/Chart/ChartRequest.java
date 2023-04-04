package com.vlazma.Dto.Chart;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartRequest {
    @NotEmpty
    private String customerId;
    private boolean checkOut;
    @NotEmpty
    private String grandTotal;
}
