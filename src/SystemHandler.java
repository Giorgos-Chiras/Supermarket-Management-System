import Customer.Customer;
import FileHandler.FileHandler;
import Products.Product;
import Products.ProductCategory;
import Users.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


class SystemHandler {

    private User user;
    private final HashMap<User, String> users = new HashMap<>();
    private final HashSet<Product> products = new HashSet<>();
    private final HashSet<Customer> customers = new HashSet<>();

    SystemHandler() {
        FileHandler fh = new FileHandler();
        fh.parseUserFile(users);
        fh.parseProductFile(products);
        fh.parseCustomerFile(customers);
        login();
    }

    private void login() {
        //Login and authenticate system
        Scanner sc = new Scanner(System.in);
        System.out.println("LOGIN PAGE\n");
        String username;
        String password;
        while (true) {
            do {
                System.out.print("Username: ");
                username = sc.nextLine();

                //Exit the System when user types exit
                if (username.equals("exit")) {
                    System.out.println("Exiting the system\n");
                    return;
                }
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
        Scanner sc = new Scanner(System.in);
        int choice;
        HRManager hrManager = ((HRManager) user);

        System.out.print("\nHR Manager Menu");
        while (true) {
            do {
                System.out.println();
                System.out.println("1. Add User");
                System.out.println("2. Print All Users");
                System.out.println("3. Search User");
                System.out.println("4. Delete User");
                System.out.println("5. Logout");

                //Get choice and perform selected action
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
            } while (choice < 0 || choice > 5);
            sc = new Scanner(System.in);

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
                    int posChoice;
                    do {
                        System.out.print("\tEnter your choice: ");
                        posChoice = sc.nextInt();
                    } while (posChoice < 1 || posChoice > 3);

                    UserPosition position = UserPosition.values()[posChoice - 1];

                    //Check if user already exists
                    if (userExists(username) || userExists(ID)) {
                        System.out.println("User with the same Username or ID already exists");
                        break;
                    }

                    hrManager.addUser(username, password, ID, position);
                    users.put(new User(username, position, ID), username);
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
                    System.out.println("Logging out...");
                    return;
            }
        }
    }


    /**
     * MANAGER MENU
     */
    private void displayManagerMenu() {
        Scanner sc = new Scanner(System.in);


        //Get choice for waht user wants to do
        System.out.println("\n**MANAGER MENU**");
        int choice;
        while (true) {
            System.out.println("\nChoose which menu you want to access");
            System.out.println("1. Product menu");
            System.out.println("2. Customer menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 3) {
                continue;
            } else if (choice == 1) {
                managerProductMenu();
            } else if (choice == 2) {
                managerCustomerMenu();
            } else {
                break;
            }
        }
        System.out.println("\nLOGGING OUT\n");
    }

    public void managerProductMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        Manager manager = ((Manager) user);
        while (true) {
            do {
                System.out.println();
                System.out.println("1. Add new product");
                System.out.println("2. Add to the stock of an item");
                System.out.println("3. Delete from the stock of an item");
                System.out.println("4. Print All Products");
                System.out.println("5. Search Product");
                System.out.println("6. Set Product Discount");
                System.out.println("7. Create Report");
                System.out.println("8. Exit");

                //Get choice and perform selected action
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
            } while (choice < 0 || choice > 8);
            sc = new Scanner(System.in);


            switch (choice) {
                //Add Product
                case 1: {
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter product ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter product price: ");
                    float price = sc.nextFloat();

                    System.out.print("Enter product quantity: ");
                    int quantity = sc.nextInt();

                    ProductCategory category = getCategory();

                    Product newProduct = new Product(name, id, price, category, quantity);
                    products.add(newProduct);
                    manager.addProduct(newProduct);
                    manager.updateProductFile(products);
                    break;
                }
                //Add to the quantity of a product
                case 2: {
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
                //Create a report based on current stock
                case 7:
                    manager.crateProductReport(products);
                    break;
                case 8:
                    return;
            }
        }
    }

    private void managerCustomerMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        Manager manager = ((Manager) user);
        while (true) {
            do {
                System.out.println("1. Add new customer");
                System.out.println("2. View all customers");
                System.out.println("3.Create customer report");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
            } while (choice < 1 || choice > 4);
            sc = new Scanner(System.in);
            switch (choice) {
                case 1: {
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer phone number: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter customer email: ");
                    String email = sc.nextLine();

                    Customer newCustomer = new Customer(name, phone, email);
                    customers.add(newCustomer);
                    manager.addCustomer(newCustomer);
                    break;
                }
                case 2: {
                    manager.printAllCustomer(customers);
                    break;
                }
                case 3: {
                    manager.createCustomerReport(customers);
                    break;
                }
                case 4: {
                    //Exits the menu
                    return;
                }
            }
        }

    }
    private void displayCashierMenu() {
        System.out.println("Cashier");
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
        Scanner sc = new Scanner(System.in);
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
        int categoryChoice;
        do {
            System.out.print("\tEnter your choice: ");
            categoryChoice = sc.nextInt();
        } while (categoryChoice < 0 || categoryChoice > 9);
        return ProductCategory.values()[categoryChoice - 1];
    }
}

