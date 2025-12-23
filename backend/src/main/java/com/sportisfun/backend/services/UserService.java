package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.UserRegistrationDto;
import com.sportisfun.backend.models.Role;
import com.sportisfun.backend.models.User;
import com.sportisfun.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional // public methods as transactions
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean userExistsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void createUser(UserRegistrationDto dto){
        User u = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(Role.USER)
                .firstname(dto.getFirstName())
                .lastname(dto.getLastName())
                .build();
        userRepository.save(u);
    }

    public ResponseEntity<Map<String, BigDecimal>> getBalance(Long userId){
        User u = userRepository.findById(userId).get();
        return ResponseEntity.ok(Collections.singletonMap("balance", u.getBalance()));
    }

}
