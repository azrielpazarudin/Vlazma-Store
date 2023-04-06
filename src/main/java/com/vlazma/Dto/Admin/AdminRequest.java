package com.vlazma.Dto.Admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty(message = "User Id Is Required")
    @Size(min=1,max=3,message = "")
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    private String userId; 
}
