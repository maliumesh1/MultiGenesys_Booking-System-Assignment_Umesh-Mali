package com.company.multigenesys_bookingsystemassignment_umeshmali.entity;


import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "resources")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type;
    private String description;
    private Integer capacity;
    private boolean active = true;
}
