package Users;

import java.io.*;

import FileHandler.*;

import java.util.HashMap;

public class HRManager extends User {
    public HRManager(String name, String id) {
        super(name, UserPosition.HR_MANAGER, id);
    }

    /**
     * Adds a user into the system
     */
    public void addUser(String username, String password, String ID, UserPosition position) {
        String[] content = new String[]{
                "Username: " + username,
                "Password: " + password,
                "ID: " + ID,
                "Position: " + position
        };

        FileHandler.addToFile("Users/users.txt", content);

    }

    /**
     Rewrites all users from the hasmap into the file
     */
    public void updateUserFile(HashMap<User, String> users) {
        String filename = "Users/users.txt";
        try {
            //Delete everything from file
            FileWriter w= new FileWriter(filename);
            w.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Iterate through all users and add them back
        for (User user : users.keySet()) {
            String username = user.getName();
            String password = users.get(user);
            String ID = user.getID();
            UserPosition position = user.getPosition();

            addUser(username, password, ID, position);
        }
    }

    /**
     * Searches if a user exists in teh systems and prints information if it does
     */

    public User searchUser(String searchKey, HashMap<User, String> users) {
        //Create a new user with ID and name of the Search key
        User searchUser = new User(searchKey, UserPosition.NONE, searchKey);
        //Iterate through Hashmap to find user
        for (User user : users.keySet()) {
            if (user.equals(searchUser)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Prints all users in the system
     */
    public void printUsers(HashMap<User, String> users) {
        for (User user : users.keySet()) {
            System.out.println(user);
        }
    }

    /**
        Function to delete a user from the system
     */
    public void deleteUser(String searchKey, HashMap<User, String> users) {
        User searchUser = new User(searchKey, UserPosition.NONE, searchKey);
        //Remove user from hashmap
        for (User user : users.keySet()) {
            if (user.equals(searchUser)) {
                users.remove(user);
                break;
            }
        }
        //Rewrite data into file
        updateUserFile(users);
    }
}


