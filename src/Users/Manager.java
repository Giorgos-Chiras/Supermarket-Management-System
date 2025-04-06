package Users;

import Customer.Customer;
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

    /**
     * PRODUCT RELATED METHODS
     */

    public void addProduct(Product product) {
        String filePath = "src/Products/products.txt";
        String[] content = new String[]{
                "Name: " + product.getProductName(),
                "ID: " + product.getProductID(),
                "Price(€): " + product.getProductPrice(),
                "Quantity: " + product.getProductQuantity(),
                "Category: " + product.getProductCategory(),
                "Discount (%): " + product.getProductDiscount()
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

    public void updateCustomerFile(HashSet<Customer> customers) {
        String filePath = "src/Customer/customers.txt";
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Customer customer : customers) {
            addCustomer(customer);
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
        time = time.replace(":", "-");
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
            FileWriter w = new FileWriter(filePath);

            String formattedTittle = String.format("%-25s %-15s %-7s %-13s %-20s %-15s",
                    "NAME", "ID", "PRICE", "QUANTITY", "CATEGORY", "DISCOUNT");
            w.write("\t\t\tPRODUCT REPORT " + date + " @ " + time.replace("-", ":") + "\n\n");
            w.write(formattedTittle);
            w.write("\n\n");
            for (int i = 0; i < productArray.length; i++) {
                String formattedProduct = String.format("%-25s %-15s %-7s %-13s %-20s %-15s",
                        name[i], id[i], "€" + price[i], quantity[i], category[i], discount[i] + "%");
                w.write(formattedProduct);
                w.write("\n");
            }
            w.close();
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
            System.out.println("No products found of " + productCategory + " category found");
        }
    }

    public void addQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The quantity must be greater than 0");
            return;
        }
        product.addQuantity(quantity);
    }

    public void removeQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The quantity must be greater than 0");
        }
        product.subtractQuantity(quantity);
    }

    public void setWeeklyDiscount(Product product) {
        product.setDiscount(10);
    }

    public void removeWeeklyDiscount(Product product) {
        product.setDiscount(0);
    }


    /**
     * CUSTOMER RELATED METHODS
     */

    public void addCustomer(Customer customer) {
        String filePath = "src/Customer/customers.txt";
        String[] content = new String[]{
                "Name: " + customer.getName(),
                "Phone number: " + customer.getPhone(),
                "Email: " + customer.getEmail(),
                "Total Spend: " + customer.getBonusCard().getTotalSpend(),
                "Reward Points: " + customer.getBonusCard().getPoints()
        };
        addToFile(filePath, content);
    }

    public void printAllCustomer(HashSet<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public Customer searchCustomer(String phoneNumber, HashSet<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public void createCustomerReport(HashSet<Customer> customers) {

        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time = time.replace(":", "-");
        String fileName = "CustomersReport" + "_" + date + "_" + time + ".txt";
        String filePath = "src/Customer/reports/" + fileName;

        String[] name = new String[customers.size()];
        String[] phone = new String[customers.size()];
        String[] email = new String[customers.size()];
        String[] totalSpend = new String[customers.size()];
        String[] points = new String[customers.size()];


        Customer[] customerArray = new Customer[customers.size()];
        customers.toArray(customerArray);

        for (int i = 0; i < customers.size(); i++) {
            name[i] = customerArray[i].getName();
            phone[i] = customerArray[i].getPhone();
            email[i] = customerArray[i].getEmail();
            totalSpend[i] = String.valueOf(customerArray[i].getBonusCard().getTotalSpend());
            points[i] = String.valueOf(customerArray[i].getBonusCard().getPoints());
        }

        try {
            FileWriter w = new FileWriter(filePath);

            String formattedTittle = String.format("%-35s %-25s %-18s %-13s %-5s",
                    "NAME", "PHONE", "\tEMAIL", "TOTAL SPEND", "POINTS");
            w.write("\t\t\tCUSTOMER REPORT " + date + " @ " + time.replace("-", ":") + "\n\n");
            w.write(formattedTittle);
            w.write("\n\n");
            for (int i = 0; i < customerArray.length; i++) {
                String formattedProduct = String.format("%-35s %-25s %-25s %-13s %-5s",
                        name[i], phone[i], email[i], totalSpend[i], points[i]);
                w.write(formattedProduct);
                w.write("\n");
            }
            w.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}




