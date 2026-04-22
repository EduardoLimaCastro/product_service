package com.eduardocastro.product_service.infrastructure.persistence.adapter;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import com.eduardocastro.product_service.infrastructure.persistence.jpa.ProductJpaRepository;
import com.eduardocastro.product_service.infrastructure.persistence.mapper.ProductJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(ProductJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Product save(Product product) {
        return ProductJpaMapper.toDomain(
                productJpaRepository.save(ProductJpaMapper.toEntity(product))
        );
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id)
                .map(ProductJpaMapper::toDomain);
    }

}
