package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private final int orderId;
    private String customerName;
    private List<CartItem> items;
    private double total;
    private orderStatus status;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = orderStatus.Pending;
        this.items = new LinkedList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public orderStatus getStatus() {
        return status;
    }

    public void setStatus(orderStatus status) {
        this.status = status;
    }

    public void addItem(CartItem item) {
        for (CartItem cartItem : items) {
            if (cartItem.getProduct().getId()==item.getProduct().getId()) {

                cartItem.setQuantity(cartItem.getQuantity()+item.getQuantity());
                calculateTotal();
                return;
            }
        }

        items.add(item);
        calculateTotal();
    }

    public void removeItem(CartItem item) {
        if (items.isEmpty()) {
            System.out.println("items is empty");
            return;
        }
        items.remove(item);
        calculateTotal();
    }

    public void calculateTotal(){
        total = 0;
        for (CartItem item : items) {
            total += item.calculateSubtotal();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId =" + orderId +
                ", customerName ='" + customerName + '\'' +
                ", items =" + items +
                ", total =" + total+
                ", status =" + status +
                '}';
    }
}
