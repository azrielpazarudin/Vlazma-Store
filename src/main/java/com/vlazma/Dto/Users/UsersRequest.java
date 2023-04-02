package com.vlazma.Dto.Users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Email Must Valid")
    @NotEmpty(message = "Email Must Not Be Empty")
    private String email;
    @NotEmpty(message = "Password Must Not Be Empty")
    @Size(max=30,min=5)
    private String password;
    @Pattern(regexp = "^[0-9]+$",message = "Role Id Must Number Format Only")
    @NotEmpty(message = "Role Id Must Not Be Empty")
    @Size(min=1)
    private String roleId;
}
