package org.example;

import java.util.*;

public class TheStore {

    List<Product> products = new LinkedList<>();
    Map<Integer, Product> productsById = new LinkedHashMap<>();
    Map<Integer, Order> orders = new LinkedHashMap<>();
    Set<String> productCategory = new HashSet<>();
    Queue<Order> ordersWaitingWToBeShipped = new LinkedList<>();
    Map<Integer, Order> ordersDelivered = new LinkedHashMap<>();
    List<Review> customerReviews = new LinkedList<>();

    public void addProduct(Product product) {

        if (productsById.containsKey(product.getId())) {
            System.out.println("Product already exists.");
            return;
        }

        products.add(product);
        productsById.put(product.getId(), product);
        productCategory.add(product.getCategory());

        System.out.println("Product added successfully.");
    }

    public void removeProduct(int id) {

        if (deleteProductEverywhere(id)) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private boolean deleteProductEverywhere(int id) {

        Product product = productsById.remove(id);

        if (product == null) {
            return false;
        }
        products.remove(product);

        boolean categoryStillUsed = false;
        for (Product p : products) {
            if (p.getCategory().equals(product.getCategory())) {
                categoryStillUsed = true;
                break;
            }
        }
        if (!categoryStillUsed) {
            productCategory.remove(product.getCategory());
        }
        return true;
    }

    public Optional<Product> searchProductBYId(int id) {
        if (productsById.containsKey(id)) {
            return Optional.of(productsById.get(id));
        }
        return Optional.empty();
    }

    public Optional<Order> searchOrderById(int orderId) {
        if (orders.containsKey(orderId)) {
            return Optional.of(orders.get(orderId));
        }
        return Optional.empty();
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("products is empty");
            return;
        }

        products.forEach(System.out::println);
    }

    public void showAllCategories() {
        if (productCategory.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }
        System.out.println("Available Categories:");
        productCategory.forEach(System.out::println);
    }

    public void displayProductsOrderedByPrice() {
        if (products.isEmpty()) {
            System.out.println("products is empty");
            return;
        }
        products.stream().sorted().forEach(System.out::println);
    }

    public void addOrder(Order order) {
        Order existingOrder = orders.putIfAbsent(order.getOrderId(), order);
        if (existingOrder != null) {
            System.out.println("Order with ID " + order.getOrderId() + " already exists.");
        }
        System.out.println("Order added successfully.");
    }

    public void addItemToOrder(int orderId, int productId, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        Product product = productsById.get(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        if (product.getStockQuantity() < quantity) {
            System.out.println("Not enough stock for product: " + product.getName());
            return;
        }

        if (order.getStatus() != orderStatus.Pending) {
            System.out.println("Cannot add items to an order that is not pending.");
            return;
        }

        CartItem item = new CartItem(product, quantity);
        order.addItem(item);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        System.out.println("Item added to order successfully.");
    }

    public void removeItemFromOrder(int orderId, int productId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        Product product = productsById.get(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (order.getItems().isEmpty()) {
            System.out.println("Order has no items to remove.");
            return;
        }
        if (order.getStatus() != orderStatus.Pending) {
            System.out.println("Cannot remove items from an order that is not pending.");
            return;
        }

        boolean itemRemoved =order.getItems().removeIf(item -> {
            if (item.getProduct().getId() == productId) {
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                return true;
            }
            return false;
        });
        if (!itemRemoved) {
            System.out.println("Item not found in the order.");
        } else {
            System.out.println("Item removed from order successfully.");
        }
    }

    public void displayOrder(int id) {

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }
        Order order = orders.get(id);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println(order);
    }

    public void addOrderToTheShippingList(int id) {
        Order order = orders.get(id);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        if (order.getItems().isEmpty()) {
            System.out.println("Cannot ship an order with no items.");
            return;
        }

        if (order.getStatus() != orderStatus.Pending) {
            System.out.println("Only pending orders can be added to the shipping list.");
            return;
        }

        if (ordersWaitingWToBeShipped.contains(order)) {
            System.out.println("Order is already in the shipping list.");
            return;
        }
        ordersWaitingWToBeShipped.add(order);
        order.setStatus(orderStatus.Shipped);
        System.out.println("Order added to the shipping list successfully.");
    }

    public void shipNextOrder() {
        if (ordersWaitingWToBeShipped.isEmpty()) {
            System.out.println("No orders waiting to be shipped.");
            return;
        }
        Order nextOrder = ordersWaitingWToBeShipped.peek();
        if (nextOrder.getItems().isEmpty()) {
            System.out.println("Cannot ship an order with no items.");
            return;
        }
        ordersWaitingWToBeShipped.poll();
        nextOrder.setStatus(orderStatus.Delivered);
        ordersDelivered.put(nextOrder.getOrderId(), nextOrder);
        System.out.println("Order shipped successfully: " + nextOrder.getOrderId());
    }

    public void cancelOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getStatus() == orderStatus.Delivered || order.getStatus() == orderStatus.Cancelled) {
            System.out.println("Cannot cancel an order that is already delivered or cancelled.");
            return;
        }
        ordersWaitingWToBeShipped.remove(order);
        order.setStatus(orderStatus.Cancelled);
        System.out.println("Order cancelled successfully: " + order.getOrderId());
    }

    public void addReviewToProduct(int productId, Review review) {
        Product product = productsById.get(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        customerReviews.add(review);
        System.out.println("Review added successfully.");

    }

    public void showAllReviewsForProduct(int productId) {
        Product product = productsById.get(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        boolean foundReviews = false;
        for (Review review : customerReviews) {
            if (review.getProductId() == productId) {
                System.out.println(review);
                foundReviews = true;
            }
        }
        if (!foundReviews) {
            System.out.println("No reviews found for the specified product.");
        }
    }

    public void removeOutOfStockProducts() {
        int count = 0;
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getStockQuantity() <= 0) {
                count++;
                iterator.remove();
                productsById.remove(product.getId());
                System.out.println("Removed out-of-stock product: " + product.getName());
            }
        }
        if (count == 0) {
            System.out.println("no product removed out-of-stock");
        }

    }

    public void displayOrdersOrderedByTotal() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }
        List<Order> orderList = new ArrayList<>(orders.values());
        Comparator<Order> byTotalComparator = Comparator.comparing(Order::getTotal);
        orderList.stream().sorted(byTotalComparator).forEach(System.out::println);
    }

}

