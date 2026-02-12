package com.group.models;

import java.util.List;

public class ShipOrder extends Order {

    public ShipOrder() {
        super();
        this.setType("ship");
    }

    public ShipOrder(String orderId, long orderDate, List<Item> items) {
        super(orderId, "ship", orderDate, items);
    }
}