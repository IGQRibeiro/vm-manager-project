package com.acme.vm_manager.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resposta paginada padrão")
public record PageResponse<T>(
        @Schema(description = "Itens da página") List<T> content,
        @Schema(example = "0") int page,
        @Schema(example = "10") int size,
        @Schema(example = "1") long totalElements,
        @Schema(example = "1") int totalPages,
        @Schema(example = "true") boolean first,
        @Schema(example = "true") boolean last
) {}
