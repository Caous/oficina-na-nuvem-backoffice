package com.oficinaapp.oficina_app.application.usecase.product;

import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteProductUseCase {

    private final ProductRepository products;
    private final ProductFinder productFinder;

    public DeleteProductUseCase(ProductRepository products, ProductFinder productFinder) {
        this.products = products;
        this.productFinder = productFinder;
    }

    @Transactional
    public void execute(Long productId, Long workshopId) {
        Product product = productFinder.findInWorkshop(productId, workshopId);

        products.delete(product);
    }
}
