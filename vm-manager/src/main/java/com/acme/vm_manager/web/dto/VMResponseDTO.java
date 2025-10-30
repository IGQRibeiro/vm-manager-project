package com.acme.vm_manager.web.dto;

import java.time.Instant;

public record VMResponseDTO(
        Long id,
        String name,
        Integer cpu,
        Integer memoryGb,
        Integer diskGb,
        String status,
        Instant createdAt
) {}
