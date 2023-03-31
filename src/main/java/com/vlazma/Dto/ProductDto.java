package com.vlazma.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    String name;
    String description;
    Integer price;
    Integer categoryId;
}
