package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdInteractorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdInteractor interactor;

    @Test
    void execute_whenProductExists_shouldReturnIt() {
        UUID id = UUID.randomUUID();
        Product product = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = interactor.execute(id);

        assertThat(result).isPresent().contains(product);
    }

    @Test
    void execute_whenProductDoesNotExist_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = interactor.execute(id);

        assertThat(result).isEmpty();
    }
}
