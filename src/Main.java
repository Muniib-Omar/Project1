package com.group;

import com.group.logic.OrderManager;
import com.group.io.JSONHandler;
import com.group.models.*;



public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }


    //Calculation Joshua Yang
    public float calculateTotalPrice(Order o){

        Order current = o;
        float total = 0;

        while(current != null){
            //multiplies by the quantity and the price and adds it to total
            total +=  current.getprice() * current.getquantity();
            //traverses the whole list
            current = current.next;
        }

        return total;
    }

    public String getUncompletedOrdersSummary(Order allOrders){

        Order current = allorders;
        StringBuilder sb = new StringBuilder();
        float total = 0;

        while(current != null){
            sb.append(current.data); //name of the item
            sb.append("\n");
            sb.append(current.getquantity());
            sb.append("\n");
            sb.append(current.getprice());
            sb.append("\n");
            total +=  current.getprice() * current.getquantity();
            current = current.next;
        }

        sb.append("Total will be: " + total);

        String result  = sb.toString();

        return result;

    }



}
