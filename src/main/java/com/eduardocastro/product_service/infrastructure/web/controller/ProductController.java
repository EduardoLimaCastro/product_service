package com.eduardocastro.product_service.infrastructure.web.controller;

import com.eduardocastro.product_service.application.usecase.ListProductsUseCase;
import com.eduardocastro.product_service.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ListProductsUseCase listProductsUseCase;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(listProductsUseCase.execute());
    }
}
