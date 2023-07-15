package models;

import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    private final static ArrayList<User> allUsers = new ArrayList<>();
    private static int id = 0;
    private final int userId;
    private String username;
    private String password;
    private String animalName;

    public User(String username, String password, String animalName) throws IOException {
        this.userId = ++id;
        this.username = username;
        this.password = password;
        this.animalName = animalName;
        User.allUsers.add(this);
    }
//    public addUserTOFile(User user){
//        try (FileWriter writer = new FileWriter("User", true)) {
//            gson.toJson(user, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
public String getAnimalName() {
    return animalName;
}

    public static User getUserByUsername(String username) {
        for (User user : User.allUsers) {
            if (user.username.equals(username)) {
                return user;
            }

        }
        return null;
    }
    public static User getUserByUserID(int ID) {
        if (ID<=id)
            return allUsers.get(ID-1);
        return null;
    }
    public int getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}