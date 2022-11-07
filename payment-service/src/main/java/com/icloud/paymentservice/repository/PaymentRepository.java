package com.icloud.paymentservice.repository;

import com.icloud.paymentservice.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentHistory, Long> {
}
