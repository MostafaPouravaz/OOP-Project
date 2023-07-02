package models;

import java.util.ArrayList;

public class Rating {
    private final static ArrayList<Rating> allRatings = new ArrayList<>();
    private int foodID;
    private int customerID;
    private int rate;
    private int counterID=0;
    private int rateID;

    public int getFoodID() {
        return foodID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getRate() {
        return rate;
    }

    public void editRate(int rate) {
        this.rate = rate;

    }

    public Rating(int foodID, int customerID, int rate) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.rate = rate;
        this.rateID = ++counterID;
        allRatings.add(this);
    }
    public static Rating getRatingByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        for (Rating allRating : allRatings) {
            if (allRating.getCustomerID() == costumer_ID && allRating.getFoodID() == foodID)
                return allRating;
        }
        return null;
    }
}
