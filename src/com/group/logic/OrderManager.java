package com.group.models;

import com.group.models.Order;
import com.group.models.OrderStatus;

public class OrderManager {

    /**
     * Requirement 5: Start an incoming order.
     * Checks if the order has already been started or completed.
     */
    public boolean startOrder(Order order) {
        if (order == null) {
            System.out.println("Error: Order is null.");
            return false;
        }

        // Check if order is already started (Using Person 1's "IN_PROGRESS" enum)
        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            System.out.println("Error: Order " + order.getOrderId() + " is already started.");
            return false;
        }

        // Check if order is already finished
        if (order.getStatus() == OrderStatus.COMPLETED) {
            System.out.println("Error: Order " + order.getOrderId() + " has already been completed.");
            return false;
        }

        // If we get here, it is safe to start
        order.setStatus(OrderStatus.IN_PROGRESS);
        System.out.println("Order " + order.getOrderId() + " has been successfully STARTED.");
        return true;
    }

    /**
     * Requirement 6: Complete an order.
     * Marks the order as done.
     */
    public void completeOrder(Order order) {
        if (order == null) {
            System.out.println("Error: Cannot complete a null order.");
            return;
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            System.out.println("Order " + order.getOrderId() + " is already marked as completed.");
            return;
        }

        order.setStatus(OrderStatus.COMPLETED);
        System.out.println("Order " + order.getOrderId() + " has been marked as COMPLETED.");
    }
}