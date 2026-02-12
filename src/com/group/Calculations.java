package com.group;

import com.group.io.JSONHandler;
import com.group.models.*;


public class Calculations {
    //Calculation Joshua Yang
    public float calculateTotalPrice(Order order) {
        float total = 0;

        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }


    public String getUncompletedOrdersSummary(Order order) {
        StringBuilder sb = new StringBuilder();
        float total = 0;

        for (Item item : order.getItems()) {
            sb.append(item.getName()).append("\n");
            sb.append(item.getQuantity()).append("\n");
            sb.append(item.getPrice()).append("\n");

            total += item.getPrice() * item.getQuantity();
        }

        sb.append("Total will be: ").append(total);
        return sb.toString();
    }


}