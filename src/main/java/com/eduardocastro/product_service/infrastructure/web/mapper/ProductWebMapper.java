package com.eduardocastro.product_service.infrastructure.web.mapper;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.infrastructure.web.dto.response.ProductResponse;

import java.time.format.DateTimeFormatter;

public class ProductWebMapper {

    private ProductWebMapper() {}

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getCreatedAt().format(FORMATTER),
                product.getUpdatedAt().format(FORMATTER)
        );
    }
}
