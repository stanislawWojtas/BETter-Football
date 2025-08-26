package com.sportisfun.backend.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @Size(max=30, message = "Username should contain max 30 characters")
    private String username;

    @Size(min = 8, message = "Password should contain at least 8 characters")
    private String password;

    @Email
    private String email;
}
