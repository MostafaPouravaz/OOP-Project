package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<User> allU = new ArrayList<>();
    private static int id = 0;

    private final int userId;
    private String username;
    private String password;
    private String animalName;

    public User(String username, String password, String animalName) {
        this.userId = ++id;
        this.username = username;
        this.password = password;
        this.animalName = animalName;
        addUser(this);
    }

    private void addUser(User user) {
        if (loadUserFromFile() != null)
            allUsers = new ArrayList<>(loadUserFromFile());
        allUsers.add(user);
        saveUserToFile();
    }

    public String getAnimalName() {
        return animalName;
    }

    public static User getUserByUsername(String username) {
        if (loadUserFromFile() != null)
            allUsers = new ArrayList<>(loadUserFromFile());
        for (User user : User.allUsers)
            if (user.username.equals(username))
                return user;
        return null;
    }
    public static User getUserByUserID(int ID) {
        if (loadUserFromFile() != null)
            allUsers = new ArrayList<>(loadUserFromFile());
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
        saveUserToFile();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        saveUserToFile();
    }
    public static void saveUserToFile(){
        try {
            FileWriter fileWriterCustomer = new FileWriter("oop\\files\\customers.json");
            Gson gsonCus = new Gson();
            allU = new ArrayList<>();
            for (User allUser : allUsers) {
                if (allUser instanceof Customer)
                    allU.add(allUser);
            }
            gsonCus.toJson(allU, fileWriterCustomer);
            fileWriterCustomer.close();
            FileWriter fileWriterVendor = new FileWriter("oop\\files\\vendors.json");
            allU = new ArrayList<>();
            for (User allUser : allUsers) {
                if (allUser instanceof Vendor)
                    allU.add(allUser);
            }
            Gson gsonVen = new Gson();
            gsonVen.toJson(allU, fileWriterVendor);
            fileWriterVendor.close();
        } catch (IOException e) {
            System.out.println("problem in writing");
        }
    }
    public static ArrayList<User> loadUserFromFile(){
        try {
            FileReader fileReaderCustomer = null;
            fileReaderCustomer = new FileReader("oop\\files\\customers.json");
            Type typeCus = new TypeToken<ArrayList<Customer>>(){}.getType();
            Gson gsonCus = new Gson();
            allU = new ArrayList<>();
            allU = gsonCus.fromJson(fileReaderCustomer,typeCus);
            fileReaderCustomer.close();
            allUsers = new ArrayList<>();
            if (allU != null)
                allUsers = (ArrayList<User>) allU.clone();
            FileReader fileReaderVendor = null;
            fileReaderVendor = new FileReader("oop\\files\\vendors.json");
            Type typeVen = new TypeToken<ArrayList<Vendor>>(){}.getType();
            allU = new ArrayList<>();
            Gson gsonVen = new Gson();
            allU = gsonVen.fromJson(fileReaderVendor,typeVen);
            fileReaderVendor.close();
            if (allU != null)
                allUsers.addAll(allU);
            id = allUsers.size();
        } catch (IOException e) {
            System.out.println("problem in reading");
        }
        return allUsers;
    }

}