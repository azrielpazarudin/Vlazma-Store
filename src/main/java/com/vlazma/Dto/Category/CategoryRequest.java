package com.vlazma.Dto.Category;
import lombok.Data;
import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "Name Is Required")
    @Size(min = 2,max=40,message = "Minimum Name Is 2 And Maximum Is 40 Character")
    private String name;
    @NotEmpty(message = "Description Is Required")
    private String description;
}
