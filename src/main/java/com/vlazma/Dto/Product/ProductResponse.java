package com.vlazma.Dto.Product;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private String category;
    private int price;
    private int stock;
    private boolean available;
    private String image;
}
