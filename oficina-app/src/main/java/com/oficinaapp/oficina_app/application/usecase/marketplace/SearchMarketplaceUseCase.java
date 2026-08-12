package com.oficinaapp.oficina_app.application.usecase.marketplace;

import com.oficinaapp.oficina_app.application.dto.marketplace.MarketplaceProductResponse;
import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.enums.ProductSort;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * The public catalogue: everything published and in stock, from every
 * workshop.
 */
@Service
public class SearchMarketplaceUseCase {

    private final ProductRepository products;
    private final SellerNameResolver sellerNames;

    public SearchMarketplaceUseCase(ProductRepository products, SellerNameResolver sellerNames) {
        this.products = products;
        this.sellerNames = sellerNames;
    }

    public List<MarketplaceProductResponse> execute(String search, ProductCategory category, ProductSort sort) {
        List<Product> found = products.findVisibleOnMarketplace(blankToNull(search), category);

        Map<Long, String> names = sellerNames.namesOf(found);
        Function<Long, String> nameOf = sellerNames.fallback(names);

        return found.stream()
                .sorted(comparatorFor(sort))
                .map(product -> MarketplaceProductResponse.from(product, nameOf.apply(product.getWorkshopId())))
                .toList();
    }

    private static String blankToNull(String search) {
        return search == null || search.isBlank() ? null : search.trim();
    }

    private static Comparator<Product> comparatorFor(ProductSort sort) {
        ProductSort effective = sort == null ? ProductSort.LOWEST_PRICE : sort;

        return switch (effective) {
            case LOWEST_PRICE -> Comparator.comparing(Product::getPrice);
            case HIGHEST_PRICE -> Comparator.comparing(Product::getPrice).reversed();
            case NAME -> Comparator.comparing(product -> product.getName().toLowerCase());
        };
    }
}
