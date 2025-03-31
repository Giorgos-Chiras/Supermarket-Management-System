package FileHandler;

import Products.Product;
import Products.ProductCategory;
import Users.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class FileHandler {
    //Default Constructor
    public FileHandler() {}


    public static void addToFile(String filePath, String[] content) {
        try {
            FileWriter w = new FileWriter(filePath, true);
            w.write("\n");
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
        while (true) {
            User tempuser = null;
            try {
                if ((line = br.readLine()) == null)
                    break;
                else if (line.isEmpty()) {
                    continue;
                } else {
                    username = line;
                    line = br.readLine();
                    password = line;
                    line = br.readLine();
                    id = line;
                    line = br.readLine();
                    position = UserPosition.valueOf(line);

                }

            } catch (IOException e) {
                System.err.println(e);
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
        while (true) {
            Product tempProduct;
            try {
                if ((line = br.readLine()) == null)
                    break;
                else if (line.isEmpty()) {
                    continue;
                } else {
                    name = line;
                    line = br.readLine();
                    id = line;
                    line = br.readLine();
                    price = Float.parseFloat(line);
                    line = br.readLine();
                    quantity = Integer.parseInt(line);
                    line = br.readLine();
                    category = ProductCategory.valueOf(line);
                    line = br.readLine();
                    discount = Integer.parseInt(line);
                    line = br.readLine();

                }
                tempProduct = new Product(name,id,price,category,quantity);

            } catch (IOException e) {
                System.err.println(e);
                throw new RuntimeException(e);
            }
            //Create object based on the position of user

            //Add object to hashmap if it doesn't exist yet
            if (products.contains(tempProduct)) {
                System.out.println("Couldn't add product " + tempProduct.getProductID() + " as a product with that ID already exists");
                continue;
            }
            products.add(tempProduct);
        }
    }
}
