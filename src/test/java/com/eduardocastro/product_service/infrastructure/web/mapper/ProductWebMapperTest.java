package com.eduardocastro.product_service.infrastructure.web.mapper;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.infrastructure.web.dto.response.ProductResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductWebMapperTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void toResponse_shouldMapAllFieldsFromDomainToResponseDto() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 20, 14, 45, 0);
        Product product = Product.reconstitute(
                id, "Laptop", "A great laptop", new BigDecimal("999.99"),
                10, "Electronics", "http://example.com/image.jpg",
                createdAt, updatedAt
        );

        ProductResponse response = ProductWebMapper.toResponse(product);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo("Laptop");
        assertThat(response.description()).isEqualTo("A great laptop");
        assertThat(response.price()).isEqualByComparingTo(new BigDecimal("999.99"));
        assertThat(response.stockQuantity()).isEqualTo(10);
        assertThat(response.category()).isEqualTo("Electronics");
        assertThat(response.imageUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(response.createdAt()).isEqualTo(createdAt.format(FORMATTER));
        assertThat(response.updatedAt()).isEqualTo(updatedAt.format(FORMATTER));
    }
}
