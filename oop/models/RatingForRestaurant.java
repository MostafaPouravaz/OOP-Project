package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RatingForRestaurant {
    private static ArrayList<RatingForRestaurant> allRatings = new ArrayList<>();
    private int restaurantID;
    private int customerID;
    private double rate;
    private static int counterID=0;
    private int rateID;

    public int getRestaurantID() {
        return restaurantID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public double getRate() {
        return rate;
    }

    public void editRate(double rate) {
        this.rate = rate;
        saveRestaurantRatingToFile();
    }

    public RatingForRestaurant(int restaurantID, int customerID, double rate) {
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.rate = rate;
        this.rateID = ++counterID;
        addRating(this);
    }
    public static RatingForRestaurant getRatingByRestaurantIDAndCostumerID(int restaurantID, int costumer_ID) {
        if (loadRestaurantRatingFromFile() != null)
            allRatings = new ArrayList<>(loadRestaurantRatingFromFile());
        for (RatingForRestaurant allRating : allRatings) {
            if (allRating.getCustomerID() == costumer_ID && allRating.getRestaurantID() == restaurantID)
                return allRating;
        }
        return null;
    }
    private void addRating(RatingForRestaurant ratingForRestaurant) {
        if (loadRestaurantRatingFromFile() != null)
            allRatings = new ArrayList<>(loadRestaurantRatingFromFile());
        allRatings.add(ratingForRestaurant);
        saveRestaurantRatingToFile();
    }
    public static void saveRestaurantRatingToFile(){
        try {
            FileWriter fileWriterRestaurantRating = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\restaurantRatings.json");
            Gson gson = new Gson();
            gson.toJson(allRatings, fileWriterRestaurantRating);
            fileWriterRestaurantRating.close();
        } catch (IOException e) {
            System.out.println("problem in writing");
        }
    }
    public static ArrayList<RatingForRestaurant> loadRestaurantRatingFromFile(){
        try {
            FileReader fileReaderRestaurantRating = null;
            fileReaderRestaurantRating = new FileReader("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\restaurantRatings.json");
            Type type = new TypeToken<ArrayList<RatingForRestaurant>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<RatingForRestaurant> allR = new ArrayList<>();
            allR = gson.fromJson(fileReaderRestaurantRating,type);
            fileReaderRestaurantRating.close();
            allRatings = new ArrayList<>();
            if (allR != null)
                allRatings.addAll(allR);
            counterID = allRatings.size();
        } catch (IOException e) {
            System.out.println("problem in reading");
        }
        return allRatings;
    }
}
