package com.icloud.orderservice.service;

import com.icloud.orderservice.controller.OrderController.OrderRequest;
import com.icloud.orderservice.entity.OrderEntity;
import com.icloud.orderservice.listener.PaymentQueueListener.PaymentMessage;
import com.icloud.orderservice.repository.OrderRepository;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    private final OrderRepository orderRepository;

    public void processOrder(OrderRequest request) {
        // 주문 진행
        var order = OrderEntity.createOrder(request);
        var savedOrder = orderRepository.save(order);
        // ORDER-SERVICE --|> PAYMENT-SERVICE 이벤트 발송
        rabbitTemplate.convertAndSend("order.process", new PaymentRequest(savedOrder.getId(), savedOrder.getOrdererName(), savedOrder.getPrice()));
    }

    @Transactional
    public void processOrderSuccess(PaymentMessage paymentMessage) {
        orderRepository.findById(paymentMessage.getOrderId())
                .ifPresent(order -> order.setOrderStatus(OrderEntity.OrderStatus.ORDER_COMPLETED));
    }
    @Transactional
    public void processOrderFailed(PaymentMessage paymentMessage) {
        orderRepository.findById(paymentMessage.getOrderId())
                .ifPresent(order -> order.setOrderStatus(OrderEntity.OrderStatus.ORDER_FAILED));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentRequest {
        private Long orderId;

        private String ordererName;

        private Double price;
    }

}
