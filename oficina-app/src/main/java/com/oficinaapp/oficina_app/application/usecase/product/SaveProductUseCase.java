package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.application.dto.product.ProductRequest;
import com.oficinaapp.oficina_app.application.dto.product.ProductResponse;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class SaveProductUseCase {

    private final ProductRepository products;
    private final ProductFinder productFinder;
    private final Clock clock;

    public SaveProductUseCase(ProductRepository products, ProductFinder productFinder, Clock clock) {
        this.products = products;
        this.productFinder = productFinder;
        this.clock = clock;
    }

    @Transactional
    public ProductResponse execute(Long productId, ProductRequest request, Long workshopId) {
        LocalDateTime now = LocalDateTime.now(clock);

        Product current = productId == null
                ? Product.builder().workshopId(workshopId).published(false).createdAt(now).build()
                : productFinder.findInWorkshop(productId, workshopId);

        Product saved = products.save(current.toBuilder()
                .name(request.name().trim())
                .description(request.description())
                .category(request.category())
                .sku(request.sku().trim())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .published(request.publishedOrDefault(current.isPublished()))
                .updatedAt(now)
                .build());

        return ProductResponse.from(saved);
    }
}
