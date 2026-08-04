package com.opensourceapi.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;

    public AuthResponse(String token, Long userId, String username) {
        this.token = token;
        this.tokenType = "Bearer";
        this.userId = userId;
        this.username = username;
    }
}
