package models;

import java.util.ArrayList;

public class RatingForFood {
    private final static ArrayList<RatingForFood> allRatings = new ArrayList<>();
    private int foodID;
    private int customerID;
    private int rate;
    private static int counterID=0;
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

    public RatingForFood(int foodID, int customerID, int rate) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.rate = rate;
        this.rateID = ++counterID;
        allRatings.add(this);
    }
    public static RatingForFood getRatingByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        for (RatingForFood allRating : allRatings) {
            if (allRating.getCustomerID() == costumer_ID && allRating.getFoodID() == foodID)
                return allRating;
        }
        return null;
    }

}
