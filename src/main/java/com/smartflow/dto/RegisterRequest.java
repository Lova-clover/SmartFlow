package com.smartflow.dto;

import com.smartflow.domain.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    private String department;
    private LocalDate joinDate;
}