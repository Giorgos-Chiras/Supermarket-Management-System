package FileHandler;

import Customer.Customer;
import Products.Product;
import Products.ProductCategory;
import Users.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
    Helper function to handle files
 */
public class FileHandler {
    /**
        Default constructor
     */
    public FileHandler() {
    }

    /**
        Adds strings to a file
        Each element of array is a new line
     */
    public static void addToFile(String filePath, String[] content) {
        try {
            FileWriter w = new FileWriter(filePath, true);

            //Check if the file is empty and add a newline if it isn't
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            if (br.readLine() != null) {
                w.write("\n");

            }
            br.close();

            //Write the content to the file
            for (String line : content) {
                w.write(line + "\n");
            }
            w.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Parses users file and adds all users to hashmap
     */
    public void parseUserFile(HashMap<User, String> users) {
        String filePath = "src/Users/users.txt";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username;
        String password;
        String id;
        UserPosition position;
        String line;
        //Get all elements of a user
        while (true) {
            User tempuser = null;
            try {
                if ((line = br.readLine()) == null)
                    break;
                //Skip newline
                else if (line.isEmpty()) {
                    continue;
                } else {
                    //Split the line to get only relevant information
                    username = line.split(": ")[1];
                    line = br.readLine();
                    password = line.split(": ")[1];
                    line = br.readLine();
                    id = line.split(": ")[1];
                    line = br.readLine();
                    position = UserPosition.valueOf(line.split(": ")[1]);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Create object based on the position of user
            if (position == UserPosition.CASHIER) {
                tempuser = new Cashier(username, id);
            } else if (position == UserPosition.MANAGER) {
                tempuser = new Manager(username, id);
            } else if (position == UserPosition.HR_MANAGER) {
                tempuser = new HRManager(username, id);
            }

            //Add object to hashmap if it doesn't exist yet
            if (tempuser != null) {
                if (users.containsKey(tempuser)) {
                    System.out.println("Couldn't add user " + tempuser.getName() + " as a user with that name already exists");
                    continue;
                }
                users.put(tempuser, password);
            } else {
                throw new RuntimeException("Couldn't add user");
            }
        }
    }

    /**
     * Parses products file and adds all products to hashset
     */
    public void parseProductFile(HashSet<Product> products) {
        String filePath = "src/Products/products.txt";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String name;
        String id;
        float price;
        ProductCategory category;
        int quantity;
        int discount;

        String line;
        //Read file and get information
        while (true) {
            Product tempProduct;
            try {
                if ((line = br.readLine()) == null)
                    break;
                //Skip newline
                else if (line.isEmpty()) {
                    continue;
                } else {
                    //Split line to get only relevant information
                    name = line.split(": ")[1];
                    line = br.readLine();
                    id = line.split(": ")[1];
                    line = br.readLine();
                    price = Float.parseFloat(line.split(": ")[1]);
                    line = br.readLine();
                    quantity = Integer.parseInt(line.split(": ")[1]);
                    line = br.readLine();
                    category = ProductCategory.valueOf(line.split(": ")[1]);
                    line = br.readLine();
                    discount = Integer.parseInt(line.split(": ")[1]);

                }
                tempProduct = new Product(name, id, price, category, quantity, discount);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Add object to hashset if it doesn't exist yet
            if (products.contains(tempProduct)) {
                System.out.println("Couldn't add product " + tempProduct.getProductID() + " as a product with that ID already exists");
                continue;
            }
            products.add(tempProduct);
        }
    }

    /**
     * Parse customer file and add customers to the hashset
     */
    public void parseCustomerFile(HashSet<Customer> customers) {
        String filePath = "src/Customer/customers.txt";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String name;
        String phone;
        String email;
        float totalSpend;
        int points;

        String line;
        //Read file
        while (true) {
            Customer tempCustomer;
            try {
                if ((line = br.readLine()) == null)
                    break;
                //Skip newline
                else if (line.isEmpty()) {
                    continue;
                } else {
                    //Split line to egt only relevant information
                    name = line.split(": ")[1];
                    line = br.readLine();
                    phone = line.split(": ")[1];
                    line = br.readLine();
                    email = line.split(": ")[1];
                    line = br.readLine();
                    totalSpend = Float.parseFloat(line.split(": ")[1]);
                    line = br.readLine();
                    points = Integer.parseInt(line.split(": ")[1]);


                }
                tempCustomer = new Customer(name, email, phone, totalSpend, points);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Add customer to hashset if it doesn't exist yet
            if (customers.contains(tempCustomer)) {
                System.out.println("Couldn't add customer " + tempCustomer.getName() + " as a product with that ID already exists");
                continue;
            }
            customers.add(tempCustomer);
        }
    }
}
