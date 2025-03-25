import Users.*;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

class SystemHandler {

    private User user;
    private HashMap<User, String> users = new HashMap<>();

    SystemHandler() {
        //Login and authenticate system
        parseUserFile();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome!");
        String username;
        String password;
        do {
            System.out.print("Username: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();
        }
        while (!authenticate(username, password));
        //Downcast to appropriate class
        System.out.println("Successfully logged in as " + username);
        System.out.println(user.getID());

        //Display correct menu
        if (user.getPosition() == Position.CASHIER) {
            displayCashierMenu();
        } else if (user.getPosition() == Position.MANAGER) {
            displayManagerMenu();
        } else if (user.getPosition() == Position.HR_MANAGER) {
            displayHRMenu();
        } else {
            System.out.println("Could not find any such position");
        }
    }

    /**
    Authenticates and logs a user into the system if the credentials is correct
     Also checks position to log into relevant system
     */
    private boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //Searches hashmap to find user that matches username given
        User searchKey = new User(username, Position.NONE, "0");
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

    private void displayHRMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome HR Manager");
        System.out.println("1. Add User");
        System.out.println("2. Print All Users");
        System.out.println("3. Search User");
        System.out.println("4. Delete User");
        System.out.println("5. Logout");

        //Get choice and perform selected action
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
            }
        } while (choice < 0 || choice > 5);
    }

    private void displayManagerMenu() {
        System.out.println("Manager");
    }

    private void displayCashierMenu() {
        System.out.println("Cashier");
    }

    //Parses users file and adds all users to hashmap
    private void parseUserFile() {
        String filePath = "src/Users.txt";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username;
        String password;
        String id;
        Position position;
        String line;
        while (true) {
            User tempuser = null;
            try {
                if ((line = br.readLine()) == null)
                    break;
                else {
                    username = line;
                    line = br.readLine();
                    password = line;
                    line = br.readLine();
                    id = line;
                    line = br.readLine();
                    position = Position.valueOf(line);


                    //Skip newline
                    line = br.readLine();
                }

            } catch (IOException e) {
                System.err.println(e);
                throw new RuntimeException(e);
            }
            //Create object based on the position of user
            if (position == Position.CASHIER) {
                tempuser = new Cashier(username, id);
            } else if (position == Position.MANAGER) {
                tempuser = new Manager(username, id);
            } else if (position == Position.HR_MANAGER) {
                tempuser = new HRManager(username, id);
            }

            //Add object to hashmap
            if (tempuser != null) {
                if (users.containsKey(tempuser)) {
                    System.out.println("Couldn't add user" + tempuser.getName() + " as a user with that name already exists");
                    continue;
                }
                users.put(tempuser, password);
            } else {
                throw new RuntimeException("Couldn't add user");
            }
        }
    }

}
