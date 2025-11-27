package com.api.cardlink.service;


import com.api.cardlink.dto.request.InviteUserRequest;
import com.api.cardlink.dto.request.RegisterRequest;
import com.api.cardlink.dto.response.AuthResponse;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse inviteUser(String tenantId, InviteUserRequest request);

}