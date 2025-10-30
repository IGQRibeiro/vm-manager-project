package com.acme.vm_manager.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "virtual_machine")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 5, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Positive @Column(nullable = false)
    private Integer cpu;

    @Positive @Column(name = "memory_gb", nullable = false)
    private Integer memoryGb;

    @Positive @Column(name = "disk_gb", nullable = false)
    private Integer diskGb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VMStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
        if (status == null) status = VMStatus.STOPPED;
    }
}
