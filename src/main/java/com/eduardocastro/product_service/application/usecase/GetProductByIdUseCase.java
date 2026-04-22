package com.eduardocastro.product_service.application.usecase;

import com.eduardocastro.product_service.domain.entity.Product;

import java.util.Optional;
import java.util.UUID;

public interface GetProductByIdUseCase {
    Optional<Product> execute(UUID id);
}
