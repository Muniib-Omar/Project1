package com.group.models;

import java.util.List;

public class PickupOrder extends Order {

    public PickupOrder() {
        super();
        this.setType("pickup");
    }

    public PickupOrder(String orderId, long orderDate, List<Item> items) {
        super(orderId, "pickup", orderDate, items);
    }
}