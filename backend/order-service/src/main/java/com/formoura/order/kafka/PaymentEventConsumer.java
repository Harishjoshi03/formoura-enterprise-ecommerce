package com.formoura.order.kafka;

import com.formoura.event.inventory.InventoryRollbackEvent;
import com.formoura.event.payment.PaymentFailedEvent;
import com.formoura.order.entity.Order;
import com.formoura.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    private final InventoryRollbackProducer rollbackProducer;

    @KafkaListener(
            topics = "payment-failed",
            groupId = "order-group")
    public void paymentFailed(
            PaymentFailedEvent event){

        Order order =
                orderService.getOrderEntity(
                        event.getOrderId());

        Map<Long,Integer> items =
                new HashMap<>();

        order.getOrderItems().forEach(item ->

                items.put(
                        item.getProductId(),
                        item.getQuantity())
        );

        InventoryRollbackEvent rollback =
                InventoryRollbackEvent.builder()

                        .orderId(order.getId())

                        .userId(order.getUserId())

                        .products(items)

                        .createdAt(LocalDateTime.now())

                        .build();

        rollbackProducer.publish(rollback);

        orderService.cancelOrder(order.getId());

    }

}