package com.group.models;

import java.util.List;

public abstract class Order {
    private String orderId;
    private String type;
    private long orderDate;
    private List<Item> items;
    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.INCOMING; // Default status for new orders
    }

    public Order(String orderId, String type, long orderDate, List<Item> items) {
        this.orderId = orderId;
        this.type = type;
        this.orderDate = orderDate;
        this.items = items;
        this.status = OrderStatus.INCOMING;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public long getOrderDate() { return orderDate; }
    public void setOrderDate(long orderDate) { this.orderDate = orderDate; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}