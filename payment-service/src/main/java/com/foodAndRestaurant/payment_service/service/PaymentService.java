package com.foodAndRestaurant.payment_service.service;

import com.foodAndRestaurant.payment_service.dtos.PaymentRequest;
import com.foodAndRestaurant.payment_service.dtos.PaymentResponse;
import com.foodAndRestaurant.payment_service.entity.Payment;
import com.foodAndRestaurant.payment_service.entity.PaymentStatus;
import com.foodAndRestaurant.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentResponse processPayment(PaymentRequest request) {

        boolean success = simulatePaymentGateway(request.getAmount());

        String transactionId = "TXN-" + UUID.randomUUID();

        Payment payment = Payment.builder().
                orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .transactionId(transactionId)
                .status(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                .amount(request.getAmount())
                .build();

        Payment saved = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(saved.getId())
                .status(saved.getStatus().name())
                .transactionId(saved.getTransactionId())
                .build();
    }

    private boolean simulatePaymentGateway(java.math.BigDecimal amount) {
        // Simple simulation: 90% success rate
        // In real life, this would call Razorpay/Stripe/PayPal API
        return Math.random() < 0.9;
    }
}
