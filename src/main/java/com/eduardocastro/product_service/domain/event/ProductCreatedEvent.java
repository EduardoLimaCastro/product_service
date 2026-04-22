package com.eduardocastro.product_service.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductCreatedEvent(
        UUID aggregateId,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String category,
        String imageUrl,
        LocalDateTime occurredAt
) implements DomainEvent {}
