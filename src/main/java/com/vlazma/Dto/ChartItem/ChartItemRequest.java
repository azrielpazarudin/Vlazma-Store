package com.vlazma.Dto.ChartItem;

import jakarta.validation.constraints.NotEmpty;
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
public class ChartItemRequest {
    @NotEmpty
    private String chartId;
    @NotEmpty
    private String productId;
    @Size(min=1,max=3,message = "Quantity Maximal 3 Digit Number And Minimum One Digit Number")
    @Pattern(regexp = "^[0-9]+$",message = "Quantity Recieve Number Format Only")
    private String quantity;
}
