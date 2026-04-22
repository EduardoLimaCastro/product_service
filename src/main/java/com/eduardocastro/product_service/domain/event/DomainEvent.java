package com.eduardocastro.product_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID aggregateId();
    LocalDateTime occurredAt();
}
