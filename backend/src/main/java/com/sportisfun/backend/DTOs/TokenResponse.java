package com.sportisfun.backend.DTOs;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String token;
    public TokenResponse(String token) {
        this.token = token;
    }
}
