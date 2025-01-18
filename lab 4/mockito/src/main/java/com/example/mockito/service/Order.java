package com.example.mockito.service;

public class Order {
    String type;
    Integer quantity;
    Integer price;

    public Order(String type, Integer quantity, Integer price) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
