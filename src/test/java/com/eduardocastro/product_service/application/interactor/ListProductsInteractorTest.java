package com.eduardocastro.product_service.application.interactor;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListProductsInteractorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductsInteractor interactor;

    @Test
    void execute_shouldReturnAllProducts() {
        Product p1 = Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img.com");
        Product p2 = Product.create("Phone", "Desc", new BigDecimal("499.99"), 20, "Electronics", "http://img2.com");
        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = interactor.execute();

        assertThat(result).hasSize(2).containsExactly(p1, p2);
    }

    @Test
    void execute_whenNoProducts_shouldReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<Product> result = interactor.execute();

        assertThat(result).isEmpty();
    }
}
