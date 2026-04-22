package com.eduardocastro.product_service.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String category,
        String imageUrl,
        String createdAt,
        String updatedAt
) {}
