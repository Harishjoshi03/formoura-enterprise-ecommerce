package com.formoura.order.serviceImp;

import com.formoura.event.order.OrderCreatedEvent;
import com.formoura.event.order.OrderItemEvent;
import com.formoura.exception.exception.BusinessException;
import com.formoura.order.client.CartClient;
import com.formoura.order.client.InventoryClient;
import com.formoura.order.client.ProductClient;
import com.formoura.order.dto.client.CartItemResponse;
import com.formoura.order.dto.client.CartResponse;
import com.formoura.order.dto.client.InventoryResponse;
import com.formoura.order.dto.client.ProductResponse;
import com.formoura.order.dto.request.OrderRequest;
import com.formoura.order.dto.response.OrderResponse;
import com.formoura.order.entity.Order;
import com.formoura.order.entity.OrderItem;
import com.formoura.order.entity.OrderStatus;
import com.formoura.order.entity.PaymentStatus;
import com.formoura.order.kafka.OrderEventProducer;
import com.formoura.order.mapper.OrderMapper;
import com.formoura.order.repository.OrderRepository;
import com.formoura.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final CartClient cartClient;

    private final ProductClient productClient;

    private final InventoryClient inventoryClient;

    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        CartResponse cart =
                cartClient.getCartByUserId(request.getUserId());

        if (cart == null || cart.getItems().isEmpty()) {
            throw new BusinessException("Cart is Empty");
        }

        Order order = mapper.toEntity(request);

        order.setOrderNumber(
                UUID.randomUUID().toString());

        order.setStatus(OrderStatus.PLACED);

        order.setPaymentStatus(PaymentStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItemResponse item : cart.getItems()) {

            ProductResponse product =
                    productClient.getProductById(item.getProductId());

            if (product == null) {

                throw new BusinessException(
                        "Product Not Found");
            }

            InventoryResponse inventory =
                    inventoryClient.getInventory(item.getProductId());

            if (!inventory.getInStock()) {

                throw new BusinessException(
                        "Product Out Of Stock");
            }

            if (inventory.getAvailableQuantity()
                    < item.getQuantity()) {

                throw new BusinessException(
                        "Insufficient Stock");
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProductId(item.getProductId());

            orderItem.setProductName(product.getProductName());

            orderItem.setPrice(product.getPrice());

            orderItem.setQuantity(item.getQuantity());

            orderItem.setTotalPrice(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );

            total = total.add(orderItem.getTotalPrice());

            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);

        Order savedOrder = repository.save(order);

        // Publish Kafka Event
        List<OrderItemEvent> items = savedOrder.getOrderItems()

                .stream()

                .map(item -> OrderItemEvent.builder()

                        .productId(item.getProductId())

                        .quantity(item.getQuantity())

                        .build())

                .toList();

        OrderCreatedEvent event = OrderCreatedEvent.builder()

                .orderId(savedOrder.getId())

                .userId(savedOrder.getUserId())

                .totalAmount(savedOrder.getTotalAmount())

                .items(items)

                .createdAt(LocalDateTime.now())

                .build();

        orderEventProducer.publish(event);

// Clear Cart
        cartClient.clearCart(request.getUserId());

        return mapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrder(Long id) {

        Order order = repository.findById(id)

                .orElseThrow(() ->
                        new BusinessException("Order Not Found"));

        return mapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return repository.findAll()

                .stream()

                .map(mapper::toResponse)

                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {

        return repository.findByUserId(userId)

                .stream()

                .map(mapper::toResponse)

                .toList();
    }



    @Override
    public OrderResponse updateOrderStatus(Long id,
                                           OrderStatus status) {

        Order order = repository.findById(id)

                .orElseThrow(() ->
                        new BusinessException("Order Not Found"));

        order.setStatus(status);

        return mapper.toResponse(repository.save(order));
    }

    @Override
    public void cancelOrder(Long id) {

        Order order = repository.findById(id)

                .orElseThrow(() ->
                        new BusinessException("Order Not Found"));

        order.setStatus(OrderStatus.CANCELLED);

        repository.save(order);
    }

}