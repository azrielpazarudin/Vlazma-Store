package com.vlazma.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int id;
    @Column(length = 254,unique = true)
    private String email;
    @Column(length = 50)
    private String password;
    @OneToOne
    @JoinColumn(name="role_id")
    private Roles role;
}
