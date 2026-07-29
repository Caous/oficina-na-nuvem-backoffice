package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.application.dto.product.ProductResponse;
import com.oficinaapp.oficina_app.application.dto.product.PublicationRequest;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * The switch between the private stock and the customer marketplace. A
 * published product still stays hidden while its stock is zero.
 */
@Service
public class PublishProductUseCase {

    private final ProductRepository products;
    private final ProductFinder productFinder;
    private final Clock clock;

    public PublishProductUseCase(ProductRepository products, ProductFinder productFinder, Clock clock) {
        this.products = products;
        this.productFinder = productFinder;
        this.clock = clock;
    }

    @Transactional
    public ProductResponse execute(Long productId, PublicationRequest request, Long workshopId) {
        Product product = productFinder.findInWorkshop(productId, workshopId);

        Product updated = product.toBuilder()
                .published(request.published())
                .updatedAt(LocalDateTime.now(clock))
                .build();

        return ProductResponse.from(products.save(updated));
    }
}
