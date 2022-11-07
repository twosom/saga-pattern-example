package com.icloud.paymentservice.entity;

import com.icloud.paymentservice.listener.OrderQueueListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.icloud.paymentservice.listener.OrderQueueListener.PaymentRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String ordererName;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public static PaymentHistory success(PaymentRequest request) {
        return PaymentHistory.builder()
                .orderId(request.getOrderId())
                .ordererName(request.getOrdererName())
                .price(request.getPrice())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();
    }

    public static PaymentHistory failed(PaymentRequest request) {
        return PaymentHistory.builder()
                .orderId(request.getOrderId())
                .ordererName(request.getOrdererName())
                .price(request.getPrice())
                .paymentStatus(PaymentStatus.FAILED)
                .build();
    }


    public enum PaymentStatus {
        SUCCESS,
        FAILED,
        REFUND;
    }


}
