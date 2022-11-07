package com.icloud.orderservice.controller;

import com.icloud.orderservice.service.OrderService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class OrderController {

    //TODO 시나리오
    // 주문 API 에 오면
    // paymentservice 와 연동하여
    // 결제를 한다.
    // 성공하면 주문 성공
    // 실패하면 주문 실패


    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<?> order(@RequestBody OrderRequest request) {
        orderService.processOrder(request);
        return ResponseEntity.ok()
                .build();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderRequest {

        private String username;
        private String address;
    }


}
