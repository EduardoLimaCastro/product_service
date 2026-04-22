package com.eduardocastro.product_service.infrastructure.persistence.jpa;

import com.eduardocastro.product_service.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {}
