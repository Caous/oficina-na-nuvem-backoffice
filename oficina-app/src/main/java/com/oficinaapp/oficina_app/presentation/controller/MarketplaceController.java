package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.marketplace.MarketplaceProductResponse;
import com.oficinaapp.oficina_app.application.usecase.marketplace.GetMarketplaceProductUseCase;
import com.oficinaapp.oficina_app.application.usecase.marketplace.SearchMarketplaceUseCase;
import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.enums.ProductSort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marketplace/products")
public class MarketplaceController {

    private final SearchMarketplaceUseCase searchMarketplace;
    private final GetMarketplaceProductUseCase getProduct;

    public MarketplaceController(
            SearchMarketplaceUseCase searchMarketplace,
            GetMarketplaceProductUseCase getProduct
    ) {
        this.searchMarketplace = searchMarketplace;
        this.getProduct = getProduct;
    }

    @GetMapping
    public List<MarketplaceProductResponse> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) ProductSort sort
    ) {
        return searchMarketplace.execute(search, category, sort);
    }

    @GetMapping("/{id}")
    public MarketplaceProductResponse get(@PathVariable Long id) {
        return getProduct.execute(id);
    }
}
