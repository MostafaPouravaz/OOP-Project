package models;

import java.util.ArrayList;

public class RatingForRestaurant {
    private final static ArrayList<RatingForRestaurant> allRatings = new ArrayList<>();
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

    }

    public RatingForRestaurant(int restaurantID, int customerID, double rate) {
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.rate = rate;
        this.rateID = ++counterID;
        allRatings.add(this);
    }
    public static RatingForRestaurant getRatingByRestaurantIDAndCostumerID(int restaurantID, int costumer_ID) {
        for (RatingForRestaurant allRating : allRatings) {
            if (allRating.getCustomerID() == costumer_ID && allRating.getRestaurantID() == restaurantID)
                return allRating;
        }
        return null;
    }


}
