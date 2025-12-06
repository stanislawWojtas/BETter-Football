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

    @Size(min=2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min=2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
}
