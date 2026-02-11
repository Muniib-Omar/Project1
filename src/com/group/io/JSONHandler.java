package com.group.io;

import com.group.models.Item;
import com.group.models.Order;
import com.group.models.OrderStatus;
import com.group.models.PickupOrder;
import com.group.models.ShipOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {

    // Gson instance with clean printing for cleaner exported JSON
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Reads orders from a JSON file.
    // 1) { "order": { ... } }   single order input
    // 2) { "orders": [ ... ] }  multiple order input
    public List<Order> readOrdersFromFile(String filename) {
        try {
            String json = Files.readString(Path.of(filename));
            List<Order> result = new ArrayList<>();

            // Try parsing as multiple orders first
            OrdersWrapper ordersWrapper = gson.fromJson(json, OrdersWrapper.class);
            if (ordersWrapper != null && ordersWrapper.orders != null && !ordersWrapper.orders.isEmpty()) {
                for (OrderJson orderJson : ordersWrapper.orders) {
                    Order mapped = mapJsonToOrder(orderJson);
                    if (mapped != null) {
                        result.add(mapped);
                    }
                }
                return result;
            }

            // backup: parse as single order
            SingleOrderWrapper singleWrapper = gson.fromJson(json, SingleOrderWrapper.class);
            if (singleWrapper != null && singleWrapper.order != null) {
                Order mapped = mapJsonToOrder(singleWrapper.order);
                if (mapped != null) {
                    result.add(mapped);
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Invalid JSON format in file: " + filename, e);
        }
    }

    // Writes all orders into one JSON file using this format:
    // { "orders": [ ... ] }
    public void writeOrdersToFile(String filename, List<Order> orders) {
        try {
            OrdersWrapper wrapper = new OrdersWrapper();
            wrapper.orders = new ArrayList<>();

            if (orders != null) {
                for (Order order : orders) {
                    wrapper.orders.add(mapOrderToJson(order));
                }
            }

            String output = gson.toJson(wrapper);
            Files.writeString(Path.of(filename), output);
        } catch (IOException e) {
            throw new RuntimeException("Could not write file: " + filename, e);
        }
    }

    // Converts raw JSON order data into model objects (ShipOrder/PickupOrder)
    private Order mapJsonToOrder(OrderJson orderJson) {
        if (orderJson == null) return null;

        Order order;
        if ("ship".equalsIgnoreCase(orderJson.type)) {
            order = new ShipOrder();
        } else if ("pickup".equalsIgnoreCase(orderJson.type)) {
            order = new PickupOrder();
        } else {
            // Unknown order type, skip this entry
            return null;
        }

        // Use provided order_id, or backup if missing in input
        String orderId = (orderJson.order_id == null || orderJson.order_id.isBlank())
                ? "AUTO-" + orderJson.order_date
                : orderJson.order_id;

        order.setOrderId(orderId);
        order.setType(orderJson.type);
        order.setOrderDate(orderJson.order_date);
        order.setItems(orderJson.items != null ? orderJson.items : new ArrayList<>());

        // Default to INCOMING if status is missing or invalid
        if (orderJson.status == null || orderJson.status.isBlank()) {
            order.setStatus(OrderStatus.INCOMING);
        } else {
            try {
                order.setStatus(OrderStatus.valueOf(orderJson.status.toUpperCase()));
            } catch (IllegalArgumentException ex) {
                order.setStatus(OrderStatus.INCOMING);
            }
        }

        return order;
    }

    // Converts Order model back to JSON-friendly fields
    private OrderJson mapOrderToJson(Order order) {
        OrderJson orderJson = new OrderJson();
        orderJson.order_id = order.getOrderId();
        orderJson.type = order.getType();
        orderJson.order_date = order.getOrderDate();
        orderJson.status = (order.getStatus() == null)
                ? OrderStatus.INCOMING.name()
                : order.getStatus().name();
        orderJson.items = (order.getItems() == null)
                ? new ArrayList<Item>()
                : order.getItems();
        return orderJson;
    }

    // Wrapper for single-order input format: { "order": { ... } }
    private static class SingleOrderWrapper {
        OrderJson order;
    }

    // Wrapper for multiple-order format: { "orders": [ ... ] }
    private static class OrdersWrapper {
        List<OrderJson> orders;
    }

    // DTO that matches JSON field names exactly
    private static class OrderJson {
        String order_id;
        String type;
        long order_date;
        String status;
        List<Item> items;
    }
}
