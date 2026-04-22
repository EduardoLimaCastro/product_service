package com.eduardocastro.product_service.application.usecase;

import com.eduardocastro.product_service.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CreateProductUseCase {
    Product execute(String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl);
}
