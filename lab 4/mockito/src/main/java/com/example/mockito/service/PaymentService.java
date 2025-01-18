package com.example.mockito.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean processPayment(Order order) throws Exception {
        Integer orderValue = calculateOrderValue(order);
        boolean success;
        try {
            System.out.println("Payment for" + orderValue + "zł processing");
            System.out.println("Payment processed");
            success = true;
        } catch (Exception e) {
            throw new Exception("Payment failed");
        }
        return success;
    }

    private Integer calculateOrderValue(Order order) {
        return order.quantity * order.price;
    }
}