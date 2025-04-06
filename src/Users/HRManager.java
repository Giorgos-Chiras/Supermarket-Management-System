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

        FileHandler.addToFile("src/Users/users.txt", content);


    }

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

    public void printUsers(HashMap<User, String> users) {
        for (User user : users.keySet()) {
            System.out.println(user);
        }
    }

    /**
     * Searches the users file for a user and deletes it, and the information about the user
     */
    public void deleteUser(String searchKey) {
        //TODO: Fix this
        String filePath = "src/Users/users.txt";
        int[] deletedLines = new int[5];  //Keep the number of the liens we want to delete
        int lineCounter = 0;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder notDeleted = new StringBuilder();  //Keep all the lines we want to keep
            while (true) {
                try {
                    //Search the file until we reach the end
                    if ((line = br.readLine()) == null) {
                        br = new BufferedReader(new FileReader(filePath));
                        //When we reach the end loop through the file again
                        for (int i = 1; i < lineCounter + 1; i++) {
                            if ((line = br.readLine()) == null) {
                                break;
                            }
                            /*
                                If we find the first line we need to delete then thant means we need to delete it and 4 more
                                So we just skip the next 5 lines as to not add them to the file
                             */
                            else if (i == deletedLines[0]) {
                                //Skip all the deleted lines
                                for (int j = 0; j < deletedLines.length - 1; j++) {
                                    br.readLine();
                                }
                            } else {
                                //Line is needed
                                notDeleted.append(line).append("\n");
                            }
                        }
                        br.close();
                        //Clear the file and write all the liens we don't delete to our file
                        FileWriter w = new FileWriter(filePath);
                        System.out.println(notDeleted);
                        w.write(notDeleted.toString());
                        w.close();
                        break;
                    } else {
                        lineCounter++;
                        if (!line.isEmpty()){
                            line = line.split(": ")[1];
                        }
                        //If search key is username
                        if (line.equals(searchKey) && lineCounter % 5 == 1) {
                            for (int i = 0; i < 5; i++) {
                                deletedLines[i] = lineCounter;
                                br.readLine();
                                lineCounter++;
                            }
                            //If search key is ID
                        } else if (line.equals(searchKey) && lineCounter % 5 == 3) {
                            for (int i = -2; i < 3; i++) {
                                deletedLines[i + 2] = lineCounter + i;
                                br.readLine();
                            }
                            lineCounter += 3;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


