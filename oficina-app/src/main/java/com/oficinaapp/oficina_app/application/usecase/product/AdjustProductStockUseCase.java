package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.application.dto.product.ProductResponse;
import com.oficinaapp.oficina_app.application.dto.product.StockAdjustmentRequest;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class AdjustProductStockUseCase {

    private final ProductRepository products;
    private final ProductFinder productFinder;
    private final Clock clock;

    public AdjustProductStockUseCase(ProductRepository products, ProductFinder productFinder, Clock clock) {
        this.products = products;
        this.productFinder = productFinder;
        this.clock = clock;
    }

    @Transactional
    public ProductResponse execute(Long productId, StockAdjustmentRequest request, Long workshopId) {
        Product product = productFinder.findInWorkshop(productId, workshopId);

        Product adjusted = product.withStockChangedBy(request.delta(), LocalDateTime.now(clock));

        return ProductResponse.from(products.save(adjusted));
    }
}
