package com.acme.vm_manager.web.dto;

import jakarta.validation.constraints.*;

public record VMCreateDTO(
        @NotBlank @Size(min = 5, max = 100) String name,
        @NotNull @Positive Integer cpu,
        @NotNull @Positive Integer memoryGb,
        @NotNull @Positive Integer diskGb
) {}
