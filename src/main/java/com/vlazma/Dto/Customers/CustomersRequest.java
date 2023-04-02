package com.vlazma.Dto.Customers;
import lombok.Data;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersRequest {
    @NotEmpty(message = "Name Is Required")
    @Size(min=3,max=50)
    private String fullName;
    @NotEmpty(message = "Gender Is Required")
    private String gender;
    @NotEmpty(message = "Date Of Birth Is Required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dateOfBirth;
    @NotEmpty(message = "Phone Number Is Required")
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    private String phoneNumber;
    @NotEmpty(message = "User Id Is Required")
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    private String userId;


}
