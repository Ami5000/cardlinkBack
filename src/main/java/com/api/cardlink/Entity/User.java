package com.api.cardlink.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank//non vide ni null
    private String password;


    private String firstName;
    private String lastName;

    private String avatarUrl;
    @CreatedDate
    @Column(name = "imported_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //Date decréation

    @LastModifiedDate
    @Column(name = "lastUpdated_at",insertable = false)
    private LocalDateTime lastUpdatedAt; // la date de la dernière mise à jour


    @ManyToOne
    @JoinColumn(name = "tenandId", referencedColumnName = "id")
    private Tenant tenant;





}
