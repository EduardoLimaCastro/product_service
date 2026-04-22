package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.application.usecase.ListProductsUseCase;
import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListProductsInteractor implements ListProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> execute() {return productRepository.findAll();}
}
