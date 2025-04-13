package Products;

import java.util.Objects;

public class Product {
    private final String productName;
    private final String productID;
    private final ProductCategory productCategory;
    private final float productPrice;
    private int productQuantity;
    private int productDiscount;

    /**
        Constructor for product
     */
    public Product(String productName, String productID, float productPrice, ProductCategory productCategory, int productQuantity,int productDiscount) {
        this.productName = productName;
        this.productID = productID;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productDiscount = productDiscount;

    }

    /**
        Returns the price amount of the discount
     */
    public float getDiscountAmount() {
        return (float) Math.floor(productDiscount * productPrice)/100;
    }

    /**
        Increases quantity of product
     */
    public void addQuantity(int quantity) {
        this.productQuantity += quantity;
    }
    /**
     Decreases quantity of product
     */
    public void subtractQuantity(int quantity) {
        this.productQuantity -= quantity;
    }

    /**
        Set discount of product
     */
    public void setDiscount(int discount) {
        this.productDiscount = discount;
    }

    /**
        Get price minus the discount
     */
    public float getFinalPrice() {
        return (float) Math.floor(productPrice * (100 - productDiscount))/100;
    }

    /**
     Getter functions
     */

    public String getProductName() {
        return productName;
    }

    public String getProductID() {
        return productID;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public boolean equals(Product product) {
        return this.productID.equals(product.productID);
    }

    /**
        Hashcode and equals functions for HashSet
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productID, product.getProductID());
    }

    @Override
    public int hashCode() {
        return productID.hashCode();
    }

    /**
        Function for printing product
     */
    public String toString() {
        String formattedPrice = String.format("%.2f", productPrice);
        return "Name: " + productName + ", ID: " + productID + ", Quantity: "
                + productQuantity + ", Price: €" + formattedPrice + ", Discount: " +productDiscount + "%";
    }

}
