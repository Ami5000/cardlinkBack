package com.api.cardlink.dto.response;


import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private String userId;
    private String email;
    private String role;
    private String tenantId;

    public AuthResponse(String message, String userId, String email, String role, String tenantId) {
        this.message = message;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.tenantId = tenantId;
    }
}