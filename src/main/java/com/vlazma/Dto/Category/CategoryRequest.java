package com.vlazma.Dto.Category;
import lombok.Data;
import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
