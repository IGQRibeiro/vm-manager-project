package com.acme.vm_manager.domain;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "vm_task_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VmTaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vm_id", nullable = false)
    private Long vmId;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 20)
    private String action;

    @Column(name = "vm_name", nullable = false, length = 100)
    private String vmName;

    /** Momento da ação (usado na tela de logs) */
    @Column(name = "timestamp", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private Instant timestamp;

    /** Data de criação do registro de log (auditoria) */
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        // garante milissegundos (sem nanos) em ambos
        if (timestamp == null) {
            timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        }
        if (createdAt == null) {
            createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        }
    }

    public static VmTaskLog of(VirtualMachine vm, String username, String action) {
        return VmTaskLog.builder()
                .vmId(vm.getId())
                .vmName(vm.getName())
                .username(username)
                .action(action)
                .timestamp(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .createdAt(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .build();
    }
}
