package com.vlazma.Dto.Customers;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersResponse {
    private int id;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private int userId;
}
