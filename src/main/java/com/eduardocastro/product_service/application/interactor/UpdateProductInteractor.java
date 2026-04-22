package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.application.usecase.UpdateProductUseCase;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProductInteractor implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean execute(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl) {
        return productRepository.findById(id)
                .map(product -> {
                    product.update(name, description, price, stockQuantity, category, imageUrl);
                    productRepository.save(product);
                    product.pullDomainEvents().forEach(eventPublisher::publishEvent);
                    return true;
                })
                .orElse(false);
    }
}
