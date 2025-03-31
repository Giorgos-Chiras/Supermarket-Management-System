package Users;
import Products.Product;
import Products.ProductCategory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import static FileHandler.FileHandler.addToFile;

public class Manager extends User {
    public Manager(String name, String id) {
        super(name, UserPosition.MANAGER, id);
    }

    public void addProduct(Product product) {
        String filePath = "src/Products/products.txt";
        String[] content = new String[]{
                product.getProductName(),
                product.getProductID(),
                String.valueOf(product.getProductPrice()),
                String.valueOf(product.getProductQuantity()),
                String.valueOf(product.getProductCategory()),
                String.valueOf(product.getProductDiscount())
        };
        addToFile(filePath, content);
    }

    /**
     * Function that rewrites data to the file
     */
    public void updateProductFile(HashSet<Product> products) {
        String filePath = "src/Products/products.txt";
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Product product : products) {
            addProduct(product);
        }
    }

    public Product searchProduct(String searchID, HashSet<Product> products) {
        for (Product product : products) {
            if (product.getProductID().equals(searchID)) {
                return product;
            }
        }
        return null;
    }


    public void crateProductReport(HashSet<Product> products) {
        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time = time.replace(":","-");
        String fileName = "ProductReport" + "_" + date + "_" + time + ".txt";
        String filePath = "src/Products/reports/" + fileName;

        String[] name = new String[products.size()];
        String[] id = new String[products.size()];
        String[] price = new String[products.size()];
        String[] quantity = new String[products.size()];
        String[] category = new String[products.size()];
        String[] discount = new String[products.size()];

        Product[] productArray = new Product[products.size()];
        products.toArray(productArray);

        for (int i = 0; i < products.size(); i++) {
            name[i] = productArray[i].getProductName();
            id[i] = productArray[i].getProductID();
            price[i] = String.valueOf(productArray[i].getProductPrice());
            quantity[i] = String.valueOf(productArray[i].getProductQuantity());
            category[i] = String.valueOf(productArray[i].getProductCategory());
            discount[i] = String.valueOf(productArray[i].getProductDiscount());
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath);

            String formattedTittle=  String.format("%-15s %-10s %-7s %-13s %-20s %-15s",
                    "NAME", "ID", "PRICE", "QUANTITY", "CATEGORY", "DISCOUNT");
            fileWriter.write("\t\t\tREPORT FOR " + date + " @ " + time.replace("-",":") + "\n\n" );
            fileWriter.write(formattedTittle);
            fileWriter.write("\n\n");
            for (int i = 0; i < productArray.length; i++) {
                String formattedProduct =  String.format("%-15s %-10s %-7s %-13s %-20s %-15s",
                        name[i], id[i],"€" + price[i], quantity[i],  category[i], discount[i] + "%");
                fileWriter.write(formattedProduct);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void printProducts(HashSet<Product> products, ProductCategory productCategory) {
        boolean isEmpty = true;
        for (Product product : products) {
            if (product.getProductCategory().equals(productCategory)) {
                System.out.println(product);
                isEmpty = false;
            }
        }
        if (isEmpty) {
            System.out.println("No products found of " + productCategory + "category found");
        }
    }

    public void addQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The product quantity must be greater than 0");
            return;
        }
        product.addQuantity(quantity);
    }

    public void removeQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The product quantity must be greater than 0");
        }
        product.subtractQuantity(quantity);
    }

    public void setWeeklyDiscount(Product product) {
        product.setDiscount(10);
    }

    public void removeWeeklyDiscount(Product product) {
        product.setDiscount(0);
    }
}
