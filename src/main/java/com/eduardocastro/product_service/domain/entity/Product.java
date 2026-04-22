package com.eduardocastro.product_service.domain.entity;

import com.eduardocastro.product_service.domain.event.DomainEvent;
import com.eduardocastro.product_service.domain.exception.InvalidProductDataException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product {

    //============
    //  Atttibutes
    //============

    private final UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    //============
    //  Constructors
    //============

    private Product(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    //============
    //  Factories
    //============

    public static Product create(String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl) {
        validate(name, price, stockQuantity, category);
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(UUID.randomUUID(), name, description, price, stockQuantity, category, imageUrl, now, now);
        // product.domainEvents.add(new ProductCreatedEvent(user.id, name, description, price, stockQuantity, category, imageUrl, now));
        return product;
    }

    public static Product reconstitute(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        validate(name, price, stockQuantity, category);
        validateReconstitute(id, createdAt, updatedAt);
        return new Product(id, name, description, price, stockQuantity, category, imageUrl, createdAt, updatedAt);
    }

    //============
    //  Domain Methods
    //============

    private void update(String name ,String description, BigDecimal price, Integer stockQuantity, String category, String imageUrl) {
        validate(name, price, stockQuantity, category);

        boolean unchanged = this.name.equals(name)
                && this.description.equals(description)
                && this.price.equals(price)
                && this.stockQuantity.equals(stockQuantity)
                && this.category.equals(category)
                && this.imageUrl.equals(imageUrl);

        if (unchanged) return;

        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
        touch();
//        domainEvents.add(new ProductUpdateEvent(id, name, description, price, stockQuantity, category, imageUrl, LocalDateTime.now() ))
    }

    private static void validate(String name, BigDecimal price, Integer stockQuantity, String category ) {
        if(name == null || name.isEmpty()) {
            throw new InvalidProductDataException("Product name cannot be null or empty");
        }
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductDataException("Product price cannot be null or zero");
        }
        if(stockQuantity == null || stockQuantity <= 0) {
            throw new InvalidProductDataException("Product stock quantity cannot be null or zero");
        }
        if(category == null || category.isEmpty()) {
            throw new InvalidProductDataException("Product category cannot be null or empty");
        }
    }

    private static void validateReconstitute(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new InvalidProductDataException("Product ID cannot be null for reconstitution");
        }
        if (createdAt == null) {
            throw new InvalidProductDataException("CreatedAt and UpdatedAt cannot be null for reconstitution");
        }
        if(updatedAt == null || updatedAt.isBefore(createdAt)) {
            throw new InvalidProductDataException("UpdatedAt cannot be before createdAt");
        }
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

//    public List<DomainEvent> pullDomainEvents() {
//        List<DomainEvent> events = Collections.unmodifiableList(new ArrayList<>(domainEvents));
//        domainEvents.clear();
//        return events;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    //============
    //  Getters
    //============

    public String getName() {return name;}
    public String getDescription() {return description;}
    public BigDecimal getPrice() {return price;}
    public Integer getStockQuantity() {return stockQuantity;}
    public String getCategory() {return category;}
    public String getImageUrl() {return imageUrl;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}

}
