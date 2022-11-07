package com.icloud.orderservice.listener;

import com.icloud.orderservice.service.OrderService;
import lombok.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentQueueListener {

    private final OrderService orderService;

    @RabbitListener(queues = "payment.succeed")
    public void listenPaymentSucceed(PaymentMessage paymentMessage) {
        orderService.processOrderSuccess(paymentMessage);
    }

    @RabbitListener(queues = "payment.failed")
    public void listenPaymentFailed(PaymentMessage paymentMessage) {
        orderService.processOrderFailed(paymentMessage);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentMessage {

        private Long orderId;
    }
}
