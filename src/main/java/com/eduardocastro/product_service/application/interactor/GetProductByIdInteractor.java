package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.application.usecase.GetProductByIdUseCase;
import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductByIdInteractor implements GetProductByIdUseCase {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> execute(UUID id) {
        return productRepository.findById(id);
    }
}
