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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductInteractorTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UpdateProductInteractor interactor;

    @Test
    void execute_whenProductExists_shouldUpdateAndReturnTrue() {
        UUID id = UUID.randomUUID();
        Product product = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        boolean result = interactor.execute(id, "Updated Laptop", "New Desc", new BigDecimal("1199.99"), 5, "Electronics", "http://img.com");

        assertThat(result).isTrue();
        verify(productRepository).save(product);
    }

    @Test
    void execute_whenProductDoesNotExist_shouldReturnFalse() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = interactor.execute(id, "Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");

        assertThat(result).isFalse();
        verify(productRepository, never()).save(any());
    }

    @Test
    void execute_withNoDomainEvents_shouldNotPublishAnything() {
        UUID id = UUID.randomUUID();
        Product product = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        interactor.execute(id, "Updated Laptop", "New Desc", new BigDecimal("1199.99"), 5, "Electronics", "http://img.com");

        verifyNoInteractions(eventPublisher);
    }
}
