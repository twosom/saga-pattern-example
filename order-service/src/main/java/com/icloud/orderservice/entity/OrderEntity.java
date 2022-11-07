package com.icloud.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.icloud.orderservice.controller.OrderController.OrderRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ordererName;

    private String address;

    private String productName;

    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static OrderEntity createOrder(OrderRequest request) {
        return OrderEntity.builder()
                .ordererName(request.getUsername())
                .address(request.getAddress())
                .productName("상품!!!!!")
                .orderStatus(OrderStatus.ORDERED)
                .price(40_000D)
                .build();
    }

    public enum OrderStatus {
        ORDERED,

        ORDER_COMPLETED,

        ORDER_FAILED;
    }

}
