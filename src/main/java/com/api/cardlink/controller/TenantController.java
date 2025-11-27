package com.api.cardlink.controller;

import com.api.cardlink.entity.Tenant;
import com.api.cardlink.dto.request.InviteUserRequest;
import com.api.cardlink.dto.response.AuthResponse;
import com.api.cardlink.dto.response.TenantResponse;
import com.api.cardlink.repository.TenantRepository;
import com.api.cardlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private UserService userService;

    @Autowired // ✅ INJECTION AJOUTÉE
    private TenantRepository tenantRepository;

    @PostMapping("/{tenantId}/invite")
    public ResponseEntity<AuthResponse> inviteUser(
            @PathVariable String tenantId,
            @RequestBody InviteUserRequest request) {
        try {
            AuthResponse response = userService.inviteUser(tenantId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(e.getMessage(), null, null, null, null));
        }
    }

    @GetMapping("/public")
    public ResponseEntity<List<TenantResponse>> getPublicTenants() {
        try {
            List<Tenant> tenants = tenantRepository.findAll();
            List<TenantResponse> response = tenants.stream()
                    .map(tenant -> new TenantResponse(
                            tenant.getId(),
                            tenant.getName(),
                            tenant.getSubdomain()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}