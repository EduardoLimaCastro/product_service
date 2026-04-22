package com.eduardocastro.product_service.domain.repository;

import com.eduardocastro.product_service.domain.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(UUID id);
}
