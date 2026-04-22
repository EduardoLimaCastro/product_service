package com.eduardocastro.product_service.infrastructure.persistence.mapper;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.infrastructure.persistence.jpa.ProductJpaEntity;

public class ProductJpaMapper {

    private ProductJpaMapper() {}

    public static ProductJpaEntity toEntity(Product product) {
        return new ProductJpaEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public static Product toDomain(ProductJpaEntity entity) {
        return Product.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getCategory(),
                entity.getImageUrl(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
