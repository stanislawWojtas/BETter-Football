package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.UserLoginDto;
import com.sportisfun.backend.DTOs.UserRegistrationDto;
import com.sportisfun.backend.models.User;
import com.sportisfun.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto dto){
        try{
            User user = userService.registerUser(dto);
            return ResponseEntity.ok("User registered successfully");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto){
        Optional<User> userOpt = userService.findByUsername(dto.getUsername());

        if(userOpt.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid username");
        }

        User user = userOpt.get();
        if(!userService.validatePassword(dto.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("Invalid password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
