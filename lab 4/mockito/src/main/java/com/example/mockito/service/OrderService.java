package com.example.mockito.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;


    public OrderService(InventoryService inventoryService, PaymentService paymentService, NotificationService notificationService) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    public boolean processOrder(Order order) throws Exception {
        boolean result = false;
        try {
            if (inventoryService.isAvailable(order) && paymentService.processPayment(order)) {
                result = notificationService.sendNotification("Order processed successfully");
            }
        } catch (Exception e) {
            throw new Exception("Order processing failed");
        }
        return result;
    }
}