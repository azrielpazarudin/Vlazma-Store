package com.vlazma.Dto.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private int id;
    private String email;
    private String password;
    private boolean active;
    private int roleId;
}
