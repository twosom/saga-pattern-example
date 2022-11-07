package com.icloud.paymentservice.listener;

import com.icloud.paymentservice.service.PaymentService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueueListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = "order.process")
    public void listen(PaymentRequest request) {
        log.info("request received = {}", request);
        //TODO 여기서는 결제 진행 해야된다.

        // 만약 orderId가 짝수면 결제 성공
        // 홀수면 결제 실패
        if (request.getOrderId() % 2 == 0) {
            paymentService.paymentSucceed(request);
        } else {
            paymentService.paymentFailed(request);
        }


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
