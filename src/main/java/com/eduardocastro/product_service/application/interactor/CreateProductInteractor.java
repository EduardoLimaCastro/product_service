package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.application.usecase.CreateProductUseCase;
import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateProductInteractor implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Product execute(String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl) {
        Product product = Product.create(name, description, price, stockQuantity, category, imageUrl);
        Product saved = productRepository.save(product);
        product.pullDomainEvents().forEach(eventPublisher::publishEvent);
        return saved;
    }

}
