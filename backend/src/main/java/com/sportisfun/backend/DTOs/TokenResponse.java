package com.sportisfun.backend.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TokenResponse {
    private String token;
    // Dodatkowo zwracamy userId i username
    private Long userId;
    private String username;
    public TokenResponse(String token, Long userId, String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
}
