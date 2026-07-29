package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

/** Loads a product only when it belongs to the caller's workshop. */
@Component
public class ProductFinder {

    private final ProductRepository products;

    public ProductFinder(ProductRepository products) {
        this.products = products;
    }

    public Product findInWorkshop(Long productId, Long workshopId) {
        return products.findByIdAndWorkshopId(productId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Product"));
    }
}
