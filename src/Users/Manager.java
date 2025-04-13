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

    /**
        Writes product into the file
     */
    public void addProduct(Product product) {
        String filePath = "src/Products/products.txt";
        //Write the content that will be included to the file as a string array
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
     * Function that rewrites data to the file and updates with current data
     */
    public void updateProductFile(HashSet<Product> products) {
        String filePath = "src/Products/products.txt";
        File file = new File(filePath);
        try {
            //Reset the file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Add all products back into the file
        for (Product product : products) {
            addProduct(product);
        }
    }

    /**
        Function that rewrites data to the customer file and updates with current data
     */
    public void updateCustomerFile(HashSet<Customer> customers) {
        String filePath = "src/Customer/customers.txt";
        File file = new File(filePath);
        try {
            //Reset the file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Add al customers to the file
        for (Customer customer : customers) {
            addCustomer(customer);
        }
    }

    /**
        Searches and returns a product in the system
     */
    public Product searchProduct(String searchID, HashSet<Product> products) {
        for (Product product : products) {
            if (product.getProductID().equals(searchID)) {
                return product;
            }
        }
        return null;
    }

    /**
        Create a report in the src/Products/reports folder which includes
        all products, product details  and current stock
     */
    public void crateProductReport(HashSet<Product> products) {
        //Create the report file name
        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time = time.replace(":", "-");
        String fileName = "ProductReport" + "_" + date + "_" + time + ".txt";
        String filePath = "src/Products/reports/" + fileName;

        //Create empty arrays
        String[] name = new String[products.size()];
        String[] id = new String[products.size()];
        String[] price = new String[products.size()];
        String[] quantity = new String[products.size()];
        String[] category = new String[products.size()];
        String[] discount = new String[products.size()];

        //Convert the HashSet to an array
        Product[] productArray = new Product[products.size()];
        products.toArray(productArray);

        //Add the corresponding details of the product to correct array
        for (int i = 0; i < products.size(); i++) {
            name[i] = productArray[i].getProductName();
            id[i] = productArray[i].getProductID();
            price[i] = String.valueOf(productArray[i].getProductPrice());
            quantity[i] = String.valueOf(productArray[i].getProductQuantity());
            category[i] = String.valueOf(productArray[i].getProductCategory());
            discount[i] = String.valueOf(productArray[i].getProductDiscount());
        }

        //Write to the report file
        try {
            FileWriter w = new FileWriter(filePath);

            //Write title of the file
            String formattedTittle = String.format("%-25s %-15s %-7s %-13s %-20s %-15s",
                    "NAME", "ID", "PRICE", "QUANTITY", "CATEGORY", "DISCOUNT");
            w.write("\t\t\tPRODUCT REPORT " + date + " @ " + time.replace("-", ":") + "\n\n");
            w.write(formattedTittle);
            w.write("\n\n");

            //Write all the products
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

    /**
        Prints all products of a category in the system
     */
    public void printProducts(HashSet<Product> products, ProductCategory productCategory) {
        //Variable that keeps track if no products of that category exist
        boolean isEmpty = true;
        //Iterate through hashset and print each product if it is part of the category
        for (Product product : products) {
            if (product.getProductCategory().equals(productCategory)) {
                //Product found
                System.out.println(product);
                isEmpty = false;
            }
        }
        //Print message if no products exist
        if (isEmpty) {
            System.out.println("No products found of " + productCategory + " category found");
        }
    }

    /**
        Increases stock of a product
     */
    public void addStock(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The quantity must be greater than 0");
            return;
        }
        product.addQuantity(quantity);
    }

    /**
        Decreases stock of a product
     */
    public void removeStock(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("The quantity must be greater than 0");
        }
        product.subtractQuantity(quantity);
    }

    /**
        Sets product as part of weekly discount (10%)
     */
    public void setWeeklyDiscount(Product product) {
        product.setDiscount(10);
    }

    /**
     Removes product from weekly discounts (0%)
     */

    public void removeWeeklyDiscount(Product product) {
        product.setDiscount(0);
    }


    /**
     * CUSTOMER RELATED METHODS
     */

    /**
        Write customer to the file
     */
    public void addCustomer(Customer customer) {
        String filePath = "src/Customer/customers.txt";
        //Write the content that will be included to the file as a string array
        String[] content = new String[]{
                "Name: " + customer.getName(),
                "Phone number: " + customer.getPhone(),
                "Email: " + customer.getEmail(),
                "Total Spend: " + customer.getBonusCard().getTotalSpend(),
                "Reward Points: " + customer.getBonusCard().getPoints()
        };
        addToFile(filePath, content);
    }

    /**
        Function that prints all customers in the system
     */
    public void printAllCustomer(HashSet<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    /**
        Searches for a customer in the systems and returns it
     */
    public Customer searchCustomer(String phoneNumber, HashSet<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }
    /**
     Create a report in the src/Customers/reports folder which includes
     all customers and details
     */
    public void createCustomerReport(HashSet<Customer> customers) {

        //Create title of file
        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time = time.replace(":", "-");
        String fileName = "CustomersReport" + "_" + date + "_" + time + ".txt";
        String filePath = "src/Customer/reports/" + fileName;

        //Create an array for each of customer details
        String[] name = new String[customers.size()];
        String[] phone = new String[customers.size()];
        String[] email = new String[customers.size()];
        String[] totalSpend = new String[customers.size()];
        String[] points = new String[customers.size()];

        //Convert hashset into array
        Customer[] customerArray = new Customer[customers.size()];
        customers.toArray(customerArray);

        //Add the corresponding details of the costumer to correct array
        for (int i = 0; i < customers.size(); i++) {
            name[i] = customerArray[i].getName();
            phone[i] = customerArray[i].getPhone();
            email[i] = customerArray[i].getEmail();
            totalSpend[i] = String.valueOf(customerArray[i].getBonusCard().getTotalSpend());
            points[i] = String.valueOf(customerArray[i].getBonusCard().getPoints());
        }

        try {
            FileWriter w = new FileWriter(filePath);
            //Write title to the file
            String formattedTittle = String.format("%-35s %-25s %-18s %-13s %-5s",
                    "NAME", "PHONE", "\tEMAIL", "TOTAL SPEND", "POINTS");
            w.write("\t\t\tCUSTOMER REPORT " + date + " @ " + time.replace("-", ":") + "\n\n");
            w.write(formattedTittle);
            w.write("\n\n");
            //Write customer details to the file
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



