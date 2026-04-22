package com.eduardocastro.product_service.application.usecase;

import com.eduardocastro.product_service.domain.entity.Product;

import java.util.List;

public interface ListProductsUseCase {
    List<Product> execute();
}
