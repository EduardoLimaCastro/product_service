package com.eduardocastro.product_service.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest (
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank double price,
    @NotBlank double stockQuantity,
    @NotBlank String category,
    @NotBlank String imageUrl
) {}
