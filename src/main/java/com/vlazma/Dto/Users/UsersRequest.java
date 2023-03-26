package com.vlazma.Dto.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {
    @Email(message = "Email Must Valid",regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email Must Not Be Empty")
    private String email;
    @NotEmpty(message = "Password Must Not Be Empty")
    private String password;
    @Pattern(regexp = "^[0-9]+$",message = "Number Format Only")
    @NotEmpty(message = "Role Id Must Not Be Empty")
    private String roleId;
}
