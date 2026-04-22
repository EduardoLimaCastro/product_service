package com.eduardocastro.product_service.application.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateProductUseCase {
    boolean execute(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl);
}
