package com.vlazma.Dto.Admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequest {
    @NotEmpty(message = "Name Is Required")
    @Size(min=3,max=50)
    private String fullName;
}
