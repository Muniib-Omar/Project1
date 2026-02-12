package com.group.ui;

import java.util.List;
import java.util.Scanner;

import com.group.models.Order;
import com.group.io.JSONHandler;
import com.group.logic.OrderManager;

public class MenuSystem {

    private final Scanner scanner = new Scanner(System.in);
    private final JSONHandler jsonHandler = new JSONHandler();
    private final OrderManager orderManager = new OrderManager();

    private List<Order> orders;

    public void start() {
        orders = jsonHandler.readOrdersFromFile("orders.json");

        int choice;
        do {
            printMenu();
            choice = readInt();

            if (choice == 1) {
                showOrders();
            } else if (choice == 2) {
                handleStartOrder();
            } else if (choice == 3) {
                handleCompleteOrder();
            } else if (choice == 0) {
                System.out.println("Goodbye.");
            } else {
                System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1) Show orders");
        System.out.println("2) Start order");
        System.out.println("3) Complete order");
        System.out.println("0) Exit");
        System.out.print("Choice: ");
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Enter a number: ");
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // clear leftover newline
        return num;
    }

    private void showOrders() {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order o : orders) {
            System.out.println(o.getOrderId() + " | " + o.getStatus());
        }
    }

    private void handleStartOrder() {
        Order o = findOrderById();
        if (o == null) return;

        try {
            orderManager.startOrder(o);
            System.out.println("Order started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleCompleteOrder() {
        Order o = findOrderById();
        if (o == null) return;

        try {
            orderManager.completeOrder(o);
            System.out.println("Order completed.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Order findOrderById() {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders loaded.");
            return null;
        }

        System.out.print("Enter order ID: ");
        String id = scanner.nextLine().trim();

        for (Order o : orders) {
            if (o.getOrderId().equals(id)) {
                return o;
            }
        }

        System.out.println("Order not found.");
        return null;
    }
}
