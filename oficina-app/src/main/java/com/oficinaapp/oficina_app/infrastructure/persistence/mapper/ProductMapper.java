package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ProductEntity;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();

        entity.setId(product.getId());
        entity.setWorkshopId(product.getWorkshopId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setCategory(product.getCategory());
        entity.setSku(product.getSku());
        entity.setPrice(product.getPrice());
        entity.setStockQuantity(product.getStockQuantity());
        entity.setPublished(product.isPublished());
        entity.setCreatedAt(product.getCreatedAt());
        entity.setUpdatedAt(product.getUpdatedAt());

        return entity;
    }

    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .workshopId(entity.getWorkshopId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .sku(entity.getSku())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .published(entity.isPublished())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
