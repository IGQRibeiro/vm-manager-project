package com.acme.vm_manager.web.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusActionDTO(@NotBlank String action) {
}