package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RatingForFood {
    private static ArrayList<RatingForFood> allRatings = new ArrayList<>();
    private int foodID;
    private int customerID;
    private double rate;
    private static int counterID=0;
    private int rateID;

    public int getFoodID() {
        return foodID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public double getRate() {
        return rate;
    }

    public void editRate(double rate) {
        this.rate = rate;
        saveFoodRatingToFile();
    }

    public RatingForFood(int foodID, int customerID, double rate) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.rate = rate;
        this.rateID = ++counterID;
        addRating(this);
    }
    public static RatingForFood getRatingByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        if (loadFoodRatingFromFile() != null)
            allRatings = new ArrayList<>(loadFoodRatingFromFile());
        for (RatingForFood allRating : allRatings) {
            if (allRating.getCustomerID() == costumer_ID && allRating.getFoodID() == foodID)
                return allRating;
        }
        return null;
    }
    private void addRating(RatingForFood ratingForFood) {
        if (loadFoodRatingFromFile() != null)
            allRatings = new ArrayList<>(loadFoodRatingFromFile());
        allRatings.add(ratingForFood);
        saveFoodRatingToFile();
    }
    public static void saveFoodRatingToFile(){
        try {
            FileWriter fileWriterFoodRating = new FileWriter("oop\\files\\foodRatings.json");
            Gson gson = new Gson();
            gson.toJson(allRatings, fileWriterFoodRating);
            fileWriterFoodRating.close();
        } catch (IOException e) {
            System.out.println("problem in writing");
        }
    }
    public static ArrayList<RatingForFood> loadFoodRatingFromFile(){
        try {
            FileReader fileReaderFoodRating = null;
            fileReaderFoodRating = new FileReader("oop\\files\\foodRatings.json");
            Type type = new TypeToken<ArrayList<RatingForFood>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<RatingForFood> allR = new ArrayList<>();
            allR = gson.fromJson(fileReaderFoodRating,type);
            fileReaderFoodRating.close();
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
