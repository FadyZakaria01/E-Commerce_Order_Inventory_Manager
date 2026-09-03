package org.example;

public class Review {
    private final int productId;
    private String customerName;
    private String comment;

    public Review(int productId, String customerName, String comment) {
        this.productId = productId;
        this.customerName = customerName;
        this.comment = comment;
    }

    public int getProductId() {
        return productId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "productId =" + productId +
                ", customerName ='" + customerName + '\'' +
                ", commit ='" + comment + '\'' +
                '}';
    }
}
