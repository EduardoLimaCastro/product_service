package com.eduardocastro.product_service.infrastructure.persistence.mapper;

import com.eduardocastro.product_service.domain.entity.Product;
import com.eduardocastro.product_service.infrastructure.persistence.jpa.ProductJpaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductJpaMapperTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Laptop";
    private static final String DESCRIPTION = "A great laptop";
    private static final BigDecimal PRICE = new BigDecimal("999.99");
    private static final Integer STOCK = 10;
    private static final String CATEGORY = "Electronics";
    private static final String IMAGE_URL = "http://example.com/image.jpg";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();

    @Test
    void toEntity_shouldMapAllFieldsFromDomainToJpaEntity() {
        Product product = Product.reconstitute(ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, CREATED_AT, UPDATED_AT);

        ProductJpaEntity entity = ProductJpaMapper.toEntity(product);

        assertThat(entity.getId()).isEqualTo(ID);
        assertThat(entity.getName()).isEqualTo(NAME);
        assertThat(entity.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(entity.getPrice()).isEqualByComparingTo(PRICE);
        assertThat(entity.getStockQuantity()).isEqualTo(STOCK);
        assertThat(entity.getCategory()).isEqualTo(CATEGORY);
        assertThat(entity.getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(entity.getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(entity.getUpdatedAt()).isEqualTo(UPDATED_AT);
    }

    @Test
    void toDomain_shouldMapAllFieldsFromJpaEntityToDomain() {
        ProductJpaEntity entity = new ProductJpaEntity(ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, CREATED_AT, UPDATED_AT);

        Product product = ProductJpaMapper.toDomain(entity);

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(product.getPrice()).isEqualByComparingTo(PRICE);
        assertThat(product.getStockQuantity()).isEqualTo(STOCK);
        assertThat(product.getCategory()).isEqualTo(CATEGORY);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(product.getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(product.getUpdatedAt()).isEqualTo(UPDATED_AT);
    }

    @Test
    void roundTrip_domainToEntityToDomain_shouldPreserveAllFields() {
        Product original = Product.reconstitute(ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, CREATED_AT, UPDATED_AT);

        Product roundTripped = ProductJpaMapper.toDomain(ProductJpaMapper.toEntity(original));

        assertThat(roundTripped.getId()).isEqualTo(original.getId());
        assertThat(roundTripped.getName()).isEqualTo(original.getName());
        assertThat(roundTripped.getPrice()).isEqualByComparingTo(original.getPrice());
        assertThat(roundTripped.getCreatedAt()).isEqualTo(original.getCreatedAt());
        assertThat(roundTripped.getUpdatedAt()).isEqualTo(original.getUpdatedAt());
    }
}
