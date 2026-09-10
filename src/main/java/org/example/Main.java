package org.example;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    static void main() {
        TheStore store = new TheStore();
        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1-> addProduct(store);

                case 2-> removeProduct(store);

                case 3-> store.displayAllProducts();

                case 4-> searchProductBYId(store);

                case 5-> store.showAllCategories();

                case 6-> store.displayProductsOrderedByPrice();

                case 7-> addOrder(store);

                case 8-> addItemToOrder(store);

                case 9-> removeItemFromOrder(store);

                case 10-> displayOrder(store);

                case 11-> addOrderToTheShippingList(store);

                case 12-> store.shipNextOrder();

                case 13-> cancelOrder(store);

                case 14-> searchOrderById(store);

                case 15-> addReviewToProduct(store);

                case 16-> showAllReviewsForProduct(store);

                case 17-> store.removeOutOfStockProducts();

                case 18-> store.displayOrdersOrderedByTotal();

                case 19-> {
                    System.out.println("Exiting the program.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("========== Menu ==========");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Display All Products");
        System.out.println("4. Search Product by ID");
        System.out.println("5. Show All Categories");
        System.out.println("6. Display Products Ordered by Price");
        System.out.println("7. Create Order");
        System.out.println("8. Add Item to Order");
        System.out.println("9. Remove Item from Order");
        System.out.println("10. Display Order");
        System.out.println("11. Add Order to the Shipping List");
        System.out.println("12. Ship Next Order");
        System.out.println("13. Cancel Order");
        System.out.println("14. Search Order by ID");
        System.out.println("15. Add Review to a Product");
        System.out.println("16. Show All Reviews for a Product");
        System.out.println("17. Remove Out-of-Stock Products");
        System.out.println("18. Display Orders Ordered by Total");
        System.out.println("19. Exit");
        System.out.println("===========================");
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    public static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    public static String readNonEmptyString(String message) {
        while (true) {
            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Name cannot be empty.");
        }
    }

    public static void addProduct(TheStore store) {
        int id = readInt("Enter product ID: ");
        String name = readNonEmptyString("Enter product name: ");
        double price = readDouble("Enter product price: ");
        String category = readNonEmptyString("Enter product category: ");
        int stockQuantity = readInt("Enter stock quantity: ");

        Product product = new Product(id, name, price, category, stockQuantity);
        store.addProduct(product);
    }

    public static void removeProduct(TheStore store) {
        int id = readInt("Enter product ID to remove: ");
        store.removeProduct(id);
    }

    public static void searchProductBYId(TheStore store) {
        int id = readInt("Enter product ID to search: ");
        store.searchProductBYId(id).ifPresentOrElse(
                product -> System.out.println("Product found: " + product),
                () -> System.out.println("Product not found.")
        );

    }

    public static void addOrder(TheStore store) {
        int orderId = readInt("Enter order ID: ");
        String customerName = readNonEmptyString("Enter customer name: ");
        Order order = new Order(orderId, customerName);
        store.addOrder(order);
    }

    public static void addItemToOrder(TheStore store) {
        int orderId = readInt("Enter order ID: ");
        int productId = readInt("Enter product ID: ");
        int quantity = readInt("Enter quantity: ");
        store.addItemToOrder(orderId, productId, quantity);
    }

    public  static void removeItemFromOrder(TheStore store) {
        int orderId = readInt("Enter order ID: ");
        int productId = readInt("Enter product ID: ");
        store.removeItemFromOrder(orderId, productId);
    }

    public static void displayOrder(TheStore store) {
        int orderId = readInt("Enter order ID: ");
        store.displayOrder(orderId);
    }

    public static void addOrderToTheShippingList(TheStore store) {
        int orderId = readInt("Enter order ID to add to the shipping list: ");
        store.addOrderToTheShippingList(orderId);
    }

    public static void cancelOrder(TheStore store) {
        int orderId = readInt("Enter order ID to cancel: ");
        store.cancelOrder(orderId);
    }

    public static void  searchOrderById(TheStore store) {
        int orderId = readInt("Enter order ID to search: ");
       Order order = store.searchOrderById(orderId).orElse(null);
        if (order != null) {
            System.out.println("Order found: " + order);
        } else {
            System.out.println("Order not found.");
        }
    }

    public static void addReviewToProduct(TheStore store) {
        int productId = readInt("Enter product ID: ");
        String reviewerName = readNonEmptyString("Enter reviewer name: ");
        String reviewText = readNonEmptyString("Enter review text: ");
        Review review = new Review(productId,reviewerName, reviewText);
        store.addReviewToProduct(productId, review);
    }

    public static void showAllReviewsForProduct(TheStore store) {
        int productId = readInt("Enter product ID: ");
        store.showAllReviewsForProduct(productId);
    }
}