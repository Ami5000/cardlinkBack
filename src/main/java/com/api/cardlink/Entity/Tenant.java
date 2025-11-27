package com.api.cardlink.Entity;

import jakarta.persistence.*;
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
@Table(name = "Tenant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tenantId;

    @CreatedDate
    @Column(name = "imported_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //Date decréation

    @LastModifiedDate
    @Column(name = "lastUpdated_at",insertable = false)
    private LocalDateTime lastUpdatedAt; // la date de la dernière mise à jour

}
