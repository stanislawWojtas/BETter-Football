package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.TokenResponse;
import com.sportisfun.backend.DTOs.UserLoginDto;
import com.sportisfun.backend.DTOs.UserRegistrationDto;
import com.sportisfun.backend.models.User;
import com.sportisfun.backend.security.CustomUserDetails;
import com.sportisfun.backend.security.JwtUtil;
import com.sportisfun.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto dto){
        if(dto.getEmail()==null || dto.getPassword()==null || dto.getUsername()==null){
            return ResponseEntity.badRequest().build();
        }
        if(userService.userExistsByEmail(dto.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        if(userService.findByUsername(dto.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        userService.createUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new TokenResponse(token));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
