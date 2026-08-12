package com.oficinaapp.oficina_app.application.usecase.marketplace;

import com.oficinaapp.oficina_app.application.dto.marketplace.OrderItemPayload;
import com.oficinaapp.oficina_app.application.dto.marketplace.OrderResponse;
import com.oficinaapp.oficina_app.application.dto.marketplace.PlaceOrderRequest;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.MarketplaceOrder;
import com.oficinaapp.oficina_app.domain.model.MarketplaceOrderItem;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.MarketplaceOrderRepository;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Turns the cart into an order. Prices come from the catalogue, never from the
 * request, and the units are taken out of stock in the same transaction — an
 * order that could not be fulfilled is never stored.
 *
 * <p>Payment is only recorded, not charged: there is no gateway yet, so every
 * order starts {@code PENDING}.
 */
@Service
public class PlaceOrderUseCase {

    private final ProductRepository products;
    private final MarketplaceOrderRepository orders;
    private final SellerNameResolver sellerNames;
    private final Clock clock;

    public PlaceOrderUseCase(
            ProductRepository products,
            MarketplaceOrderRepository orders,
            SellerNameResolver sellerNames,
            Clock clock
    ) {
        this.products = products;
        this.orders = orders;
        this.sellerNames = sellerNames;
        this.clock = clock;
    }

    @Transactional
    public OrderResponse execute(PlaceOrderRequest request, Long customerId) {
        LocalDateTime now = LocalDateTime.now(clock);
        List<MarketplaceOrderItem> items = new ArrayList<>();

        for (OrderItemPayload payload : request.items()) {
            Product product = products.findVisibleOnMarketplaceById(payload.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product"));

            products.save(product.withStockChangedBy(-payload.quantity(), now));

            items.add(MarketplaceOrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(payload.quantity())
                    .sellerWorkshopId(product.getWorkshopId())
                    .sellerName(sellerNames.nameOf(product.getWorkshopId()))
                    .build());
        }

        MarketplaceOrder order = MarketplaceOrder.place(
                customerId,
                request.paymentMethod(),
                request.deliveryAddress().toDomain(),
                items,
                now
        );

        return OrderResponse.from(orders.save(order));
    }
}
