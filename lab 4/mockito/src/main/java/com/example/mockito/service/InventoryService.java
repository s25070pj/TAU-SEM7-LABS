package com.example.mockito.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {
    Map<String, Integer> inventory = new HashMap<>();

    public boolean isAvailable(Order order) {
        Integer itemsInStock = inventory.get(order.getType());
        return itemsInStock >= order.getQuantity();
    }
}