package com.icloud.paymentservice.service;

import com.icloud.paymentservice.entity.PaymentHistory;
import com.icloud.paymentservice.repository.PaymentRepository;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.icloud.paymentservice.listener.OrderQueueListener.PaymentRequest;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    public void paymentSucceed(PaymentRequest request) {
        PaymentHistory history = PaymentHistory.success(request);
        paymentRepository.save(history);
        rabbitTemplate.convertAndSend("payment.succeed", new PaymentMessage(request.getOrderId()));
    }

    public void paymentFailed(PaymentRequest request) {
        PaymentHistory history = PaymentHistory.failed(request);
        paymentRepository.save(history);
        rabbitTemplate.convertAndSend("payment.failed", new PaymentMessage(request.getOrderId()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class PaymentMessage {

        private Long orderId;
    }

}
