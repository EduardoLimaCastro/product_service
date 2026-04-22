package com.eduardocastro.product_service.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="products")
public class ProductJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name="stock_quantity" ,nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String category;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ProductJpaEntity() {}

    public ProductJpaEntity(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public BigDecimal getPrice() {return price;}
    public Integer getStockQuantity() {return stockQuantity;}
    public String getCategory() {return category;}
    public String getImageUrl() {return imageUrl;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}
}
