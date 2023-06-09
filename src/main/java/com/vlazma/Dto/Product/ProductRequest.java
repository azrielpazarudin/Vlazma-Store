package com.vlazma.Dto.Product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    @Size(max=3)
    private String categoryId;
    @NotEmpty
    @Size(max=9)
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    private String price;
    @NotEmpty
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    @Size(max=5)
    private String stock;
    private boolean available;
    @NotEmpty
    private String image;
}
