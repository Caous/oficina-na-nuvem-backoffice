package com.oficinaapp.oficina_app.application.usecase.marketplace;

import com.oficinaapp.oficina_app.application.dto.marketplace.MarketplaceProductResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetMarketplaceProductUseCase {

    private final ProductRepository products;
    private final SellerNameResolver sellerNames;

    public GetMarketplaceProductUseCase(ProductRepository products, SellerNameResolver sellerNames) {
        this.products = products;
        this.sellerNames = sellerNames;
    }

    public MarketplaceProductResponse execute(Long productId) {
        Product product = products.findVisibleOnMarketplaceById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product"));

        return MarketplaceProductResponse.from(product, sellerNames.nameOf(product.getWorkshopId()));
    }
}
