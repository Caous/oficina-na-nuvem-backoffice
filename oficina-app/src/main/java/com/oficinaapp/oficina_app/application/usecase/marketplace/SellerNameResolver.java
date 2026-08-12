package com.oficinaapp.oficina_app.application.usecase.marketplace;

import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.domain.repository.WorkshopRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Names the sellers of a product list in a single query, instead of one lookup
 * per product.
 */
@Component
public class SellerNameResolver {

    private static final String UNKNOWN_SELLER = "Unknown workshop";

    private final WorkshopRepository workshops;

    public SellerNameResolver(WorkshopRepository workshops) {
        this.workshops = workshops;
    }

    public Map<Long, String> namesOf(List<Product> products) {
        List<Long> workshopIds = products.stream()
                .map(Product::getWorkshopId)
                .distinct()
                .toList();

        return workshops.findAllById(workshopIds).stream()
                .collect(Collectors.toMap(Workshop::getId, Workshop::getTradeName));
    }

    public String nameOf(Long workshopId) {
        return workshops.findById(workshopId)
                .map(Workshop::getTradeName)
                .orElse(UNKNOWN_SELLER);
    }

    public Function<Long, String> fallback(Map<Long, String> names) {
        return workshopId -> names.getOrDefault(workshopId, UNKNOWN_SELLER);
    }
}
