package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findByIdAndWorkshopId(Long id, Long workshopId);

    /** Private stock of one workshop, published or not. */
    List<Product> findByWorkshopId(Long workshopId);

    /**
     * Marketplace view: published and in stock, from every workshop.
     *
     * @param search  matches the name, or null for everything
     * @param category filters by category, or null for every category
     */
    List<Product> findVisibleOnMarketplace(String search, ProductCategory category);

    Optional<Product> findVisibleOnMarketplaceById(Long id);

    void delete(Product product);
}
