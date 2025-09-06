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

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean userExistsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void createUser(String username, String email, String password){
        User u = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(Role.USER)
                .build();
        userRepository.save(u);
    }

}
