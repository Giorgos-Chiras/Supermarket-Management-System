package Products;

public class Product {
    private final String productName;
    private final String productID;
    private final ProductCategory productCategory;
    private final float productPrice;
    private int productQuantity;
    private int productDiscount;

    public Product(String productName, String productID, float productPrice, ProductCategory productCategory, int productQuantity,int productDiscount) {
        this.productName = productName;
        this.productID = productID;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productDiscount = productDiscount;

    }

    public float getDiscountAmount() {
        return (float) Math.floor(productDiscount * productPrice)/100;
    }

    public void addQuantity(int quantity) {
        this.productQuantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.productQuantity -= quantity;
    }

    public void setDiscount(int discount) {
        this.productDiscount = discount;
    }


    public float getFinalPrice() {
        return (float) Math.floor(productPrice * (100 - productDiscount))/100;
    }

    //Getters
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

    public boolean equals(String productID) {
        return this.productID.equals(productID);
    }

    @Override
    public int hashCode() {
        return productID.hashCode();
    }

    public String toString() {
        String formattedPrice = String.format("%.2f", productPrice);
        return "Name: " + productName + ", ID: " + productID + ", Quantity: "
                + productQuantity + ", Price: €" + formattedPrice + ", Discount: " +productDiscount + "%";
    }

}
