package com.vlazma.Dto.Chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartResponse {
    private int id;
    private int customerId;
    private boolean checkOut;
    private int grandTotal;
}
