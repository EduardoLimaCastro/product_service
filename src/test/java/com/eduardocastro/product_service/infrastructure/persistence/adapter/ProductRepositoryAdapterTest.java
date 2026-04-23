package com.eduardocastro.product_service.infrastructure.persistence.adapter;

import com.eduardocastro.product_service.TestcontainersConfiguration;
import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional
class ProductRepositoryAdapterTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save_shouldPersistProductAndReturnItWithSameId() {
        Product product = Product.create("Laptop", "A great laptop", new BigDecimal("999.99"), 10, "Electronics", "http://example.com/image.jpg");

        Product saved = productRepository.save(product);

        assertThat(saved.getId()).isEqualTo(product.getId());
        assertThat(saved.getName()).isEqualTo("Laptop");
        assertThat(saved.getPrice()).isEqualByComparingTo(new BigDecimal("999.99"));
    }

    @Test
    void findById_whenProductExists_shouldReturnIt() {
        Product product = Product.create("Phone", "A smartphone", new BigDecimal("599.99"), 20, "Mobile", "http://example.com/phone.jpg");
        productRepository.save(product);

        Optional<Product> found = productRepository.findById(product.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(product.getId());
        assertThat(found.get().getName()).isEqualTo("Phone");
    }

    @Test
    void findById_whenProductDoesNotExist_shouldReturnEmpty() {
        Optional<Product> found = productRepository.findById(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllSavedProducts() {
        productRepository.save(Product.create("Laptop", "Desc", new BigDecimal("999.99"), 10, "Electronics", "http://img1.com"));
        productRepository.save(Product.create("Phone", "Desc", new BigDecimal("499.99"), 20, "Mobile", "http://img2.com"));

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSizeGreaterThanOrEqualTo(2);
    }
}
