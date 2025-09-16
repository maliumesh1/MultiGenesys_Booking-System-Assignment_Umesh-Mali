package com.company.multigenesys_bookingsystemassignment_umeshmali.entity;

import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.ReservationStatus;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.*;
        import lombok.*;
        import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "reservations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resource_id")
    private ResourceEntity resource;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private Instant startTime;
    private Instant endTime;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
