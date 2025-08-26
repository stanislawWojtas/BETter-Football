package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.UserRegistrationDto;
import com.sportisfun.backend.models.Role;
import com.sportisfun.backend.models.User;
import com.sportisfun.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional // public methods as transactions
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDto dto){
        if(userRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
