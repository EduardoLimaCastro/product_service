package com.eduardocastro.product_service.domain.entity;

import com.eduardocastro.product_service.domain.exception.InvalidProductDataException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static final String NAME = "Laptop";
    private static final String DESCRIPTION = "A great laptop";
    private static final BigDecimal PRICE = new BigDecimal("999.99");
    private static final Integer STOCK = 10;
    private static final String CATEGORY = "Electronics";
    private static final String IMAGE_URL = "http://example.com/image.jpg";

    // --- Product.create() ---

    @Test
    void create_withValidData_shouldReturnProductWithCorrectFields() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);

        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(product.getPrice()).isEqualByComparingTo(PRICE);
        assertThat(product.getStockQuantity()).isEqualTo(STOCK);
        assertThat(product.getCategory()).isEqualTo(CATEGORY);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(product.getCreatedAt()).isNotNull();
        assertThat(product.getUpdatedAt()).isNotNull();
    }

    @Test
    void create_shouldSetCreatedAtAndUpdatedAtToTheSameTimestamp() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);
        assertThat(product.getCreatedAt()).isEqualTo(product.getUpdatedAt());
    }

    @Test
    void create_withNullName_shouldThrow() {
        assertThatThrownBy(() -> Product.create(null, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("name");
    }

    @Test
    void create_withEmptyName_shouldThrow() {
        assertThatThrownBy(() -> Product.create("", DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    @Test
    void create_withNullPrice_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, null, STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("price");
    }

    @Test
    void create_withZeroPrice_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, BigDecimal.ZERO, STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    @Test
    void create_withNegativePrice_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, new BigDecimal("-1.00"), STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    @Test
    void create_withNullStockQuantity_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, PRICE, null, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("stock quantity");
    }

    @Test
    void create_withZeroStockQuantity_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, PRICE, 0, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    @Test
    void create_withNullCategory_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, PRICE, STOCK, null, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("category");
    }

    @Test
    void create_withEmptyCategory_shouldThrow() {
        assertThatThrownBy(() -> Product.create(NAME, DESCRIPTION, PRICE, STOCK, "", IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    // --- Product.reconstitute() ---

    @Test
    void reconstitute_withValidData_shouldReturnProductWithCorrectFields() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        Product product = Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, createdAt, updatedAt);

        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getCreatedAt()).isEqualTo(createdAt);
        assertThat(product.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void reconstitute_withEqualCreatedAtAndUpdatedAt_shouldSucceed() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Product product = Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now);

        assertThat(product).isNotNull();
    }

    @Test
    void reconstitute_withNullId_shouldThrow() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> Product.reconstitute(null, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("ID");
    }

    @Test
    void reconstitute_withNullCreatedAt_shouldThrow() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, null, now))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("CreatedAt");
    }

    @Test
    void reconstitute_withNullUpdatedAt_shouldThrow() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, null))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("UpdatedAt");
    }

    @Test
    void reconstitute_withUpdatedAtBeforeCreatedAt_shouldThrow() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = createdAt.minusSeconds(1);
        assertThatThrownBy(() -> Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, createdAt, updatedAt))
                .isInstanceOf(InvalidProductDataException.class)
                .hasMessageContaining("UpdatedAt");
    }

    // --- product.update() ---

    @Test
    void update_withNewValues_shouldUpdateAllFields() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);
        String newName = "Gaming Laptop";
        BigDecimal newPrice = new BigDecimal("1499.99");
        Integer newStock = 5;
        String newCategory = "Gaming";
        String newDescription = "A powerful gaming laptop";
        String newImageUrl = "http://example.com/gaming.jpg";

        product.update(newName, newDescription, newPrice, newStock, newCategory, newImageUrl);

        assertThat(product.getName()).isEqualTo(newName);
        assertThat(product.getDescription()).isEqualTo(newDescription);
        assertThat(product.getPrice()).isEqualByComparingTo(newPrice);
        assertThat(product.getStockQuantity()).isEqualTo(newStock);
        assertThat(product.getCategory()).isEqualTo(newCategory);
        assertThat(product.getImageUrl()).isEqualTo(newImageUrl);
    }

    @Test
    void update_withSameValues_shouldNotChangeUpdatedAt() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);
        LocalDateTime originalUpdatedAt = product.getUpdatedAt();

        product.update(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);

        assertThat(product.getUpdatedAt()).isEqualTo(originalUpdatedAt);
    }

    @Test
    void update_withInvalidData_shouldThrow() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);
        assertThatThrownBy(() -> product.update(null, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL))
                .isInstanceOf(InvalidProductDataException.class);
    }

    // --- pullDomainEvents() ---

    @Test
    void pullDomainEvents_shouldReturnListAndClearEvents() {
        Product product = Product.create(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL);

        List<?> events = product.pullDomainEvents();

        assertThat(events).isEmpty();
        assertThat(product.pullDomainEvents()).isEmpty();
    }

    // --- equals / hashCode ---

    @Test
    void equals_productsWithSameId_shouldBeEqual() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Product p1 = Product.reconstitute(id, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now);
        Product p2 = Product.reconstitute(id, "Different Name", DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now);

        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }

    @Test
    void equals_productsWithDifferentId_shouldNotBeEqual() {
        LocalDateTime now = LocalDateTime.now();
        Product p1 = Product.reconstitute(UUID.randomUUID(), NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now);
        Product p2 = Product.reconstitute(UUID.randomUUID(), NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, IMAGE_URL, now, now);

        assertThat(p1).isNotEqualTo(p2);
    }
}
