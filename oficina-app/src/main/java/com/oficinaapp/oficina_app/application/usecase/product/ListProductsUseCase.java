package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.application.dto.product.ProductResponse;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsUseCase {

    private final ProductRepository products;

    public ListProductsUseCase(ProductRepository products) {
        this.products = products;
    }

    public List<ProductResponse> execute(Long workshopId) {
        return products.findByWorkshopId(workshopId).stream()
                .map(ProductResponse::from)
                .toList();
    }
}
