import Customer.Customer;
import FileHandler.FileHandler;
import Products.Product;
import Products.ProductCategory;
import Transaction.Transaction;
import Users.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


class SystemHandler {

    private User user;
    private final HashMap<User, String> users = new HashMap<>();
    private final HashSet<Product> products = new HashSet<>();
    private final HashSet<Customer> customers = new HashSet<>();
    private final HashMap<Cashier, Float> cashiers = new HashMap<>();
    private final HashMap<Customer, float[]> customerPoints = new HashMap<>();

    SystemHandler() {
        FileHandler fh = new FileHandler();
        fh.parseUserFile(users);
        fh.parseProductFile(products);
        fh.parseCustomerFile(customers);

        //Update files so they are always in the order they are in the hashmap/set
        Manager manager = new Manager("", "");
        manager.updateProductFile(products);
        manager.updateCustomerFile(customers);
        int choice;
        do {
            System.out.println();
            login();
            System.out.println("1.Login");
            System.out.println("2.Exit System");
            choice = getChoice(1, 2);
        } while (choice == 1);

        //Terminate Program
        createClosureReport();
        System.out.println("Exiting System");
    }

    private void login() {
        //Login and authenticate system
        Scanner sc = new Scanner(System.in);
        System.out.println("LOGIN PAGE\n");
        String username;
        String password;

        do {
            System.out.print("Username: ");
            username = sc.nextLine();

            System.out.print("Password: ");
            password = sc.nextLine();
        }
        while (!authenticate(username, password));

        System.out.println("Successfully logged in as " + username);

        //Display correct menu and downcast to appropriate class
        if (user.getPosition() == UserPosition.CASHIER) {
            displayCashierMenu();
        } else if (user.getPosition() == UserPosition.MANAGER) {
            displayManagerMenu();
        } else if (user.getPosition() == UserPosition.HR_MANAGER) {
            displayHRMenu();
        } else {
            System.out.println("Could not find any such position");
        }

    }

    /**
     * Authenticates and logs a user into the system if the credentials is correct
     * Also checks position to log into relevant system
     */
    private boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //Searches hashmap to find user that matches username given
        User searchKey = new User(username, UserPosition.NONE, "0");
        for (User tempUser : users.keySet()) {
            if (tempUser.equals(searchKey)) {
                if (users.get(tempUser).equals(password)) {
                    user = tempUser;
                    return true;
                }
            }
        }
        System.out.println("Wrong username or password");
        return false;
    }

    /**
     * HR MANAGER MENU
     */
    private void displayHRMenu() {
        HRManager hrManager = ((HRManager) user);
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHR Manager Menu");
        while (true) {
            System.out.println();
            System.out.println("1. Add User");
            System.out.println("2. Print All Users");
            System.out.println("3. Search User");
            System.out.println("4. Delete User");
            System.out.println("5. Logout");

            //Get choice and perform selected action
            int choice = getChoice(0, 5);
            switch (choice) {
                //  Add User
                case 1:
                    //Get input from user
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    System.out.print("Enter ID: ");
                    String ID = sc.nextLine();
                    System.out.println("Choose position");
                    System.out.println("\t1. HR Manager");
                    System.out.println("\t2. Manager");
                    System.out.println("\t3. Cashier");
                    int posChoice = getChoice(0, 3);


                    UserPosition position = UserPosition.values()[posChoice - 1];

                    //Check if user already exists
                    if (userExists(username) || userExists(ID)) {
                        System.out.println("User with the same Username or ID already exists");
                        break;
                    }

                    hrManager.addUser(username, password, ID, position);
                    if(position == UserPosition.CASHIER) {
                        users.put(new Cashier(username,  ID), password);
                    }
                    else if (position == UserPosition.MANAGER) {
                        users.put(new Manager(username,  ID), password);
                    }
                    else if (position == UserPosition.HR_MANAGER) {
                        users.put(new HRManager(username,  ID), password);
                    }

                    System.out.println("Successfully added user " + username + " with ID " + ID);
                    break;

                case 2:
                    hrManager.printUsers(users);
                    break;
                case 3: {
                    System.out.print("Enter username or ID: ");
                    String searchKey = sc.nextLine();
                    User searchUser = hrManager.searchUser(searchKey, users);
                    if (searchUser != null) {
                        System.out.println("User found");
                        System.out.println(searchUser);
                    } else {
                        System.out.println("User not found");
                    }
                    break;
                }
                case 4:
                    System.out.print("Enter username or ID: ");
                    String searchKey = sc.nextLine();
                    User searchUser = hrManager.searchUser(searchKey, users);
                    if (searchUser != null) {
                        if (searchUser.equals(user)) {
                            System.out.println("Can't delete user that is currently using the system");
                            break;
                        }
                        hrManager.deleteUser(searchKey);
                        users.remove(searchUser);
                        System.out.println("Successfully deleted user " + searchKey);
                    } else {
                        System.out.println("User not found");
                    }
                    break;

                case 5:
                    System.out.println("Logging out...\n");
                    return;
            }
        }
    }


    /**
     * MANAGER MENU
     */
    private void displayManagerMenu() {
        //Get choice for want user wants to do
        System.out.println("\n**MANAGER MENU**");
        int choice;
        while(true) {
        System.out.println("\nChoose which menu you want to access");
        System.out.println("1. Product menu");
        System.out.println("2. Customer menu");
        System.out.println("3. Logout");

            choice = getChoice(1, 3);
            if (choice == 1) {
                managerProductMenu();
            } else if (choice == 2) {
                managerCustomerMenu();
            } else {
                System.out.println("\nLogging out\n");
                return;
            }
        }

    }

    public void managerProductMenu() {
        Scanner sc;
        Manager manager = ((Manager) user);
        while (true) {

            System.out.println();
            System.out.println("1. Add new product");
            System.out.println("2. Add to the stock of an item");
            System.out.println("3. Delete from the stock of an item");
            System.out.println("4. Print All Products");
            System.out.println("5. Search Product");
            System.out.println("6. Add product to weekly discounts");
            System.out.println("7. Remove product from weekly discount");
            System.out.println("8. Create Report");
            System.out.println("9. Exit menu");

            //Get choice and perform selected action
            int choice = getChoice(1, 9);

            switch (choice) {
                //Add Product
                case 1: {
                    sc = new Scanner(System.in);
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter product ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter product price: ");
                    float price = sc.nextFloat();

                    System.out.print("Enter product quantity: ");
                    int quantity = sc.nextInt();

                    ProductCategory category = getCategory();

                    Product newProduct = new Product(name, id, price, category, quantity, 0);
                    products.add(newProduct);
                    manager.addProduct(newProduct);
                    manager.updateProductFile(products);
                    break;
                }
                //Add to the quantity of a product
                case 2: {
                    sc = new Scanner(System.in);
                    Product product = getProduct();
                    if (product != null) {
                        System.out.print("Enter quantity to be added: ");
                        int quantity = sc.nextInt();
                        manager.addQuantity(product, quantity);
                        manager.updateProductFile(products);
                    }
                    break;
                }
                //Remove from the quantity of a product
                case 3: {
                    sc = new Scanner(System.in);
                    Product product = getProduct();
                    if (product != null) {
                        System.out.print("Enter quantity to be removed: ");
                        int quantity = sc.nextInt();
                        manager.removeQuantity(product, quantity);
                        manager.updateProductFile(products);
                    }
                    break;
                }
                //Prints all products of a chosen category
                case 4: {
                    ProductCategory category = getCategory();
                    manager.printProducts(products, category);
                    break;
                }
                //Search for product using ID
                case 5: {
                    Product product = getProduct();
                    if (product != null) {
                        System.out.println(product);
                    }
                    break;
                }
                //Set a product to have weekly discount(10%)
                case 6: {
                    Product product = getProduct();
                    if (product != null) {
                        manager.setWeeklyDiscount(product);
                    }
                    manager.updateProductFile(products);
                    break;
                }
                //Remove product from weekly discounts
                case 7: {
                    Product product = getProduct();
                    if (product != null) {
                        manager.removeWeeklyDiscount(product);
                    }
                    manager.updateProductFile(products);
                    break;
                }
                //Create a report based on current stock
                case 8:
                    manager.crateProductReport(products);
                    System.out.println("Successfully created product report");
                    break;
                case 9:
                    System.out.println();
                    return;
            }
        }
    }

    private void managerCustomerMenu() {
        Scanner sc = new Scanner(System.in);
        Manager manager = ((Manager) user);
        while (true) {

            System.out.println("1. Add new customer");
            System.out.println("2. View all customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Create customer report");
            System.out.println("5. Exit menu");
            int choice = getChoice(1, 5);
            System.out.println();


            switch (choice) {
                case 1: {
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer phone number: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter customer email: ");
                    String email = sc.nextLine();

                    Customer newCustomer = new Customer(name, email, phone);
                    if (customers.contains(newCustomer)) {
                        System.out.println("Phone number already registered to another customer. Cant register customer");
                        break;
                    }

                    //Add the new customer
                    customers.add(newCustomer);
                    manager.addCustomer(newCustomer);

                    //Update so file matches hashset
                    manager.updateCustomerFile(customers);

                    System.out.println("Successfully added customer " + newCustomer.getName() +"\n");
                    break;
                }
                case 2: {
                    manager.printAllCustomer(customers);
                    System.out.println();
                    break;
                }
                case 3: {
                    System.out.print("Enter phone number: ");
                    String phone = sc.nextLine();
                    Customer customer = manager.searchCustomer(phone, customers);
                    if (customer != null) {
                        System.out.println(customer + " " + customer.getBonusCard() +"\n");
                    } else {
                        System.out.println("Customer not found\n");
                    }
                    break;
                }

                case 4: {
                    manager.createCustomerReport(customers);
                    System.out.println("Customer report created\n");
                    break;
                }
                case 5: {
                    //Exits the menu
                    return;
                }
            }
        }

    }

    private void displayCashierMenu() {
        System.out.println("** CASHIER **");

        Scanner sc;
        Cashier cashier = ((Cashier) user);
        while (true) {

            System.out.println("1. Make a transaction");
            System.out.println("2. Exit");
            int choice = getChoice(1, 2);

            switch (choice) {

                case 1: {
                    sc = new Scanner(System.in);
                    if (!cashiers.containsKey(cashier)) {
                        cashiers.put(cashier, 0f);
                    }
                    Transaction transaction = new Transaction(cashier);
                    Product product;
                    float total = 0;
                    int quantity;
                    System.out.println("Enter products ID or -1 to exit");
                    while (true) {
                        //Get the product
                        do {
                            product = getProduct();
                        } while (product == null);

                        //Exiting
                        if (product.getProductID().equals("-1")) {
                            System.out.println("Completing Transaction");
                            System.out.println("Final Total: €" + total);
                            //Ask for bonus card
                            char ch;
                            do {
                                //Bonus card
                                System.out.print("Does customer have a bonus card? (Y/N): ");
                                sc = new Scanner(System.in);
                                ch = sc.next().charAt(0);
                                Customer currCustomer;
                                if (ch == 'Y') {
                                    do {
                                        //Get the customer if customer has bonus card
                                        System.out.print("Enter customer phone number: ");
                                        sc = new Scanner(System.in);
                                        String phone = sc.nextLine();
                                        //Break out in case of mistake
                                        if (phone.equals("-1")) {
                                            currCustomer = null;
                                            break;
                                        }
                                        //Search for customer
                                        Manager manager = new Manager("", "");
                                        currCustomer = manager.searchCustomer(phone, customers);
                                        if (currCustomer == null) {
                                            System.out.println("Customer not found");
                                        }
                                    } while (currCustomer == null);
                                    //Ask customer if they want to use rewards points
                                    if (currCustomer != null) {
                                        float[] transactionInfo = new float[]{0f, 0f,0f};
                                        if (!customerPoints.containsKey(currCustomer)) {
                                            customerPoints.put(currCustomer, transactionInfo);
                                        }
                                        transaction.setCustomer(currCustomer);
                                        int currentPoints = currCustomer.getBonusCard().getPoints();
                                        System.out.println("Reward Points: " + currentPoints);
                                        do {
                                            System.out.print("Use reward points?(Y/N): ");
                                            ch = sc.next().charAt(0);
                                            if (ch == 'Y') {
                                                //Points used
                                                customerPoints.get(currCustomer)[1] += currentPoints;
                                                currentPoints = 0;
                                            }
                                        } while (ch != 'Y' && ch != 'N');

                                        float noDiscountTotal = total;

                                        //Apply discount and put used points into hashmap
                                        if (ch == 'Y') {
                                            transaction.setDiscount(currCustomer.getBonusCard().getPoints());
                                            total = currCustomer.getBonusCard().useDiscount(total);
                                        }
                                        //Update bonus card based on total without discount
                                        currCustomer.getBonusCard().updateBonusCard(noDiscountTotal);
                                        //Put points earned on hashmap
                                        if (currCustomer.getBonusCard().getPoints() > currentPoints) {
                                            customerPoints.get(currCustomer)[0] += currentPoints;
                                        }

                                        customerPoints.get(currCustomer)[2] +=total;
                                    } else {
                                        continue;
                                    }
                                }
                                //Finish transaction

                                transaction.createReceipt();
                                if (cashiers.containsKey(cashier)) {
                                    Float cashierTotal = cashiers.get(cashier);
                                    cashiers.put(cashier, cashierTotal + total);
                                } else {
                                    cashiers.put(cashier, total);
                                }
                                System.out.println("Final Total: " + total);
                                System.out.println("Transaction Completed");
                                break;
                            } while (true);
                            break;
                        }
                        //Get the amount of items purchases
                        System.out.print("Amount: ");
                        quantity = sc.nextInt();
                        //Check if quantity is available and remove it
                        if (product.getProductQuantity() >= quantity) {
                            transaction.addProduct(product, quantity);
                            total += quantity * product.getFinalPrice();
                            product.subtractQuantity(quantity);

                        } else {
                            System.out.println("Only " + product.getProductQuantity() + " available.Transaction not completed");
                        }
                        System.out.println("Current total: €" + total);
                    }
                    //Update the products file
                    Manager manager = new Manager("", "");
                    manager.updateProductFile(products);
                    manager.updateCustomerFile(customers);
                    break;
                }
                case 2:
                    System.out.println("Logging out\n");
                    return;
            }
        }
    }

    private int getChoice(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

        } while (choice < min || choice > max);
        return choice;
    }

    /**
     * Helper function that checks if a user exists in the system
     */
    private boolean userExists(String key) {
        User tempUser = new User(key, UserPosition.NONE, key);
        for (User user : users.keySet()) {
            if (user.equals(tempUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to get a product from the hashset
     */
    private Product getProduct() {
        Manager tempManager = new Manager("temp", "temp");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String searchKey = sc.nextLine();
        //In case user want to exit transaction
        if (searchKey.equals("-1")) {
            return new Product("-1", "-1", -1, ProductCategory.NONE, 0, -0);
        }
        Product searchProduct = tempManager.searchProduct(searchKey, products);
        if (searchProduct == null) {
            System.out.println("Product not found");
        }
        return searchProduct;
    }

    /**
     * Helper function to get and return a product category
     */
    private ProductCategory getCategory() {
        System.out.print("Choose product category\n");
        System.out.print("\t1. Produce\n");
        System.out.print("\t2. Dairy\n");
        System.out.print("\t3. Meat & Seafood\n");
        System.out.print("\t4. Bakery\n");
        System.out.print("\t5. Beverages\n");
        System.out.print("\t6. Frozen\n");
        System.out.print("\t7. Pantry\n");
        System.out.print("\t8. Household\n");
        System.out.print("\t9. Personal Care\n");
        int categoryChoice = getChoice(1,9);
        return ProductCategory.values()[categoryChoice - 1];
    }

    private void createClosureReport() {
        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time = time.replace(":", "-");

        String fileName = "ClosureReport" + "_" + date + "_" + time + ".txt";
        String filePath = "src/closure_reports/" + fileName;

        try {
            FileWriter w = new FileWriter(filePath);
            w.write("\t\tCLOSURE REPORT " + date + " @ " + time.replace("-", ":") + "\n");
            w.write("\n\n**PRODUCTS STOCK**\n\n");

            String formattedProductTitle = String.format("%-25s %-10s", "Product Name" , "Quantity");
            w.write(formattedProductTitle + "\n");
            for (Product product : products) {
                String formattedProduct = String.format("%-25s %-10s", product.getProductName() , product.getProductQuantity());
                w.write(formattedProduct + "\n");
            }
            //Write cashiers of the day(Name, Total Sales)
            if (cashiers.isEmpty()) {
                w.write("No transactions made today\n");
            } else {
                w.write("\n** CASHIERS **\n\n");
                String formattedCashierTitle = String.format("%-20s %-10s", "Cashier" , "Total Sales");
                w.write(formattedCashierTitle + "\n");
                for (Cashier cashier : cashiers.keySet()) {
                    String formattedCashier = String.format("%-20s %-10s", cashier.getName(),
                            " " + cashiers.get(cashier));
                    w.write(formattedCashier);
                }
            }
            //Write customers of the day (Name, Points earned/used, total spend)
            if (!customerPoints.isEmpty()) {
                w.write("\n\n** CUSTOMERS **\n\n");
                String formattedCustomerTitle = String.format("%-20s %-15s %-15s %-15s",
                        "Name" , "Total Spend", "Points Earned", "Points Used");
                w.write(formattedCustomerTitle + "\n");
                for (Customer customer : customerPoints.keySet()) {
                    String formattedCustomer = String.format("%-20s %-15s %-15s %-15s",
                            customer.getName(), customerPoints.get(customer)[2], (int) customerPoints.get(customer)[0], (int) customerPoints.get(customer)[1]);
                    w.write(formattedCustomer + "\n");
                }
            }
            w.close();
            System.out.println("\n Closure report created\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}



