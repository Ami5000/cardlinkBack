package com.api.cardlink.service.impl;

import com.api.cardlink.entity.Tenant;
import com.api.cardlink.entity.User;
import com.api.cardlink.enumeration.UserRole;
import com.api.cardlink.dto.request.InviteUserRequest;
import com.api.cardlink.dto.request.RegisterRequest;
import com.api.cardlink.dto.response.AuthResponse;
import com.api.cardlink.repository.UserRepository;
import com.api.cardlink.repository.TenantRepository;
import com.api.cardlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Vérifier email unique
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("Email déjà utilisé", null, null, null, null);
        }

        // Créer utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        Tenant tenant = null;

        // SCÉNARIO 1: Créer un nouveau tenant (TENANT_ADMIN)
        if (request.getCompanyName() != null && !request.getCompanyName().isBlank()) {
            tenant = createTenant(request.getCompanyName());
            user.setTenant(tenant);
            user.setRole(UserRole.TENANT_ADMIN);
        }
        // SCÉNARIO 2: Rejoindre un tenant existant (AGENT)
        else if (request.getTenantId() != null && !request.getTenantId().isBlank()) {
            tenant = tenantRepository.findById(request.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));
            user.setTenant(tenant);
            user.setRole(UserRole.AGENT);
        }
        // SCÉNARIO 3: Erreur - doit choisir une entreprise
        else {
            return new AuthResponse("Vous devez choisir ou créer une entreprise", null, null, null, null);
        }

        user = userRepository.save(user);

        return new AuthResponse(
                "Inscription réussie",
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                tenant != null ? tenant.getId() : null
        );
    }

    @Override
    public AuthResponse inviteUser(String tenantId, InviteUserRequest request) {
        // 1. Vérifier que le tenant existe
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant non trouvé"));

        // 2. Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // 3. Créer l'utilisateur invité
        User invitedUser = new User();
        invitedUser.setEmail(request.getEmail());
        invitedUser.setFirstName(request.getFirstName());
        invitedUser.setLastName(request.getLastName());
        invitedUser.setPhone(request.getPhone());
        invitedUser.setRole(request.getRole());
        invitedUser.setTenant(tenant);
        invitedUser.setPassword("INVITED"); // Mot de passe temporaire

        invitedUser = userRepository.save(invitedUser);

        return new AuthResponse(
                "Utilisateur invité avec succès",
                invitedUser.getId(),
                invitedUser.getEmail(),
                invitedUser.getRole().name(),
                tenant.getId()
        );
    }

    private Tenant createTenant(String companyName) {
        String subdomain = generateSubdomain(companyName);

        Tenant tenant = new Tenant();
        tenant.setName(companyName);
        tenant.setSubdomain(subdomain);
        return tenantRepository.save(tenant);
    }

    private String generateSubdomain(String companyName) {
        String base = companyName.toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");

        String subdomain = base;
        int counter = 1;
        while (tenantRepository.existsBySubdomain(subdomain)) {
            subdomain = base + "-" + counter;
            counter++;
        }

        return subdomain;
    }
}