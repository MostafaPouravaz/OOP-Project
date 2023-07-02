package models;

import enums.FoodType;

import java.util.ArrayList;
import java.util.Collections;

public class Restaurant {
    private final static ArrayList<Restaurant> allRestaurant = new ArrayList<>();
    private String name;
    int ID_Counter=0;
    private int ID;
    private int ID_Owner;
    private int locationNode;
    private ArrayList<Integer> foodType = new ArrayList<>(); //
    private ArrayList<Food> food = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Owner() {
        return ID_Owner;
    }

    public void setID_Owner(int ID_Owner) {
        this.ID_Owner = ID_Owner;
    }

    public int getLocationNode() {
        return locationNode;
    }

    public void setLocationNode(int locationNode) {
        this.locationNode = locationNode;
    }

    public ArrayList<Integer> getFoodType() {
        return foodType;
    }

    public FoodType getFoodType(int ID) {
        return FoodType.getFoodTypeFromInt(ID);
    }

    public void setFoodType(int ID) {
        this.foodType.add(ID);
    }

    public ArrayList<Food> getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food.add(food);
    }

    public Restaurant(String name, int ID_Owner, int locationNode) {
        ID=++ID_Counter;
        this.name = name;
        this.ID_Owner = ID_Owner;
        this.locationNode = locationNode;
        allRestaurant.add(this);
    }
    public static ArrayList<Restaurant> getAllRestaurant() {
        return Restaurant.allRestaurant;
    }
    public static Restaurant getRestaurantByRestaurantName(String name) {
        for (Restaurant restaurant : Restaurant.allRestaurant) {
            if (restaurant.name.equals(name)) {
                return restaurant;
            }

        }
        return null;
    }

    public static Restaurant getRestaurantByRestaurantID(int ID) {
        if(ID<=allRestaurant.size())
            return allRestaurant.get(ID-1);
        return null;
    }
    public void addRestaurant(){
        Restaurant.allRestaurant.add(this);
    }

}
