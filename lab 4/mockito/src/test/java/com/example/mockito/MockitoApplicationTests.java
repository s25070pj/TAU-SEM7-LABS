package com.example.mockito;

import com.example.mockito.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockitoApplicationTests {
    @Mock
    private InventoryService inventoryService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("tea", 2, 15);
        orderService = new OrderService(inventoryService, paymentService, notificationService);
    }

    @Test
    public void testProcessOrder_orderSuccess() throws Exception {
        when(inventoryService.isAvailable(order)).thenReturn(true);
        when(paymentService.processPayment(order)).thenReturn(true);
        when(notificationService.sendNotification("Order processed successfully")).thenReturn(true);
        boolean result = orderService.processOrder(order);

        assertTrue(result);
    }

    @Test
    public void testProcessOrder_orderOutOfStock() throws Exception {
        when(inventoryService.isAvailable(order)).thenReturn(false);
        boolean result = orderService.processOrder(order);

        assertFalse(result);
    }

    @Test
    public void testProcessOrder_paymentFailed() throws Exception {
        when(inventoryService.isAvailable(order)).thenReturn(true);
        when(paymentService.processPayment(order)).thenReturn(false);
        boolean result = orderService.processOrder(order);

        assertFalse(result);
    }

    @Test
    public void testProcessOrder_paymentThrowsException() throws Exception {
        when(inventoryService.isAvailable(order)).thenReturn(true);
        when(paymentService.processPayment(order)).thenThrow(new Exception("Payment failed"));
        Exception thrown = assertThrows(Exception.class, () -> orderService.processOrder(order));

        assertEquals(thrown.getMessage(), "Order processing failed");
    }

}