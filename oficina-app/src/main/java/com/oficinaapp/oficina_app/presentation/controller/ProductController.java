package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.product.ProductRequest;
import com.oficinaapp.oficina_app.application.dto.product.ProductResponse;
import com.oficinaapp.oficina_app.application.dto.product.PublicationRequest;
import com.oficinaapp.oficina_app.application.dto.product.StockAdjustmentRequest;
import com.oficinaapp.oficina_app.application.usecase.product.AdjustProductStockUseCase;
import com.oficinaapp.oficina_app.application.usecase.product.DeleteProductUseCase;
import com.oficinaapp.oficina_app.application.usecase.product.ListProductsUseCase;
import com.oficinaapp.oficina_app.application.usecase.product.PublishProductUseCase;
import com.oficinaapp.oficina_app.application.usecase.product.SaveProductUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** The workshop's private stock. The customer never reaches this path. */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ListProductsUseCase listProducts;
    private final SaveProductUseCase saveProduct;
    private final DeleteProductUseCase deleteProduct;
    private final AdjustProductStockUseCase adjustStock;
    private final PublishProductUseCase publishProduct;

    public ProductController(
            ListProductsUseCase listProducts,
            SaveProductUseCase saveProduct,
            DeleteProductUseCase deleteProduct,
            AdjustProductStockUseCase adjustStock,
            PublishProductUseCase publishProduct
    ) {
        this.listProducts = listProducts;
        this.saveProduct = saveProduct;
        this.deleteProduct = deleteProduct;
        this.adjustStock = adjustStock;
        this.publishProduct = publishProduct;
    }

    @GetMapping
    public List<ProductResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listProducts.execute(caller.requireWorkshopId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveProduct.execute(null, request, caller.requireWorkshopId());
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveProduct.execute(id, request, caller.requireWorkshopId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        deleteProduct.execute(id, caller.requireWorkshopId());
    }

    /** Entry and exit of units; a negative delta takes stock out. */
    @PatchMapping("/{id}/stock")
    public ProductResponse adjustStock(
            @PathVariable Long id,
            @Valid @RequestBody StockAdjustmentRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return adjustStock.execute(id, request, caller.requireWorkshopId());
    }

    @PatchMapping("/{id}/publish")
    public ProductResponse publish(
            @PathVariable Long id,
            @Valid @RequestBody PublicationRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return publishProduct.execute(id, request, caller.requireWorkshopId());
    }
}
