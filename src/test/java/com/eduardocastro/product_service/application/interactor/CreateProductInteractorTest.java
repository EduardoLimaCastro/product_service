package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductInteractorTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreateProductInteractor interactor;

    @Test
    void execute_shouldSaveProductAndReturnIt() {
        Product savedProduct = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = interactor.execute("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");

        assertThat(result).isEqualTo(savedProduct);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void execute_withNoDomainEvents_shouldNotPublishAnything() {
        Product savedProduct = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        interactor.execute("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");

        verifyNoInteractions(eventPublisher);
    }
}
