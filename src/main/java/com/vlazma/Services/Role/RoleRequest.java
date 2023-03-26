package com.vlazma.Services.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String roleName;
}
