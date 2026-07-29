package com.oficinaapp.oficina_app.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "workshop_customer",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_workshop_customer",
                columnNames = {"workshop_id", "customer_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkshopCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workshop_id", nullable = false)
    private Long workshopId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "linked_at", nullable = false)
    private LocalDateTime linkedAt;
}
