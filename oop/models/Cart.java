package models;

import enums.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cart {
    private ArrayList<Food> chosenFoods = new ArrayList<>();
    private final static ArrayList<Cart> allPersonCart = new ArrayList<>();

    private int customerID;
    private int restaurantID;
    public Cart(int customerID, int restaurantID) {
        this.customerID = customerID;
        this.restaurantID = restaurantID;
        allPersonCart.add(this);
    }
    public void addFood(Food chosenFood){
        this.chosenFoods.add(chosenFood);
        System.out.println(Message.SUCCESS);
    }
    public void deleteFood(Food chosenFood){
        chosenFoods.remove(chosenFood);
        System.out.println(Message.SUCCESS);
    }
    public ArrayList<Food> getChosenFoods() {
        return chosenFoods;
    }
    public int getCustomerID() {
        return customerID;
    }
    public int getRestaurantID() {
        return restaurantID;
    }
    public static ArrayList<Cart> getAllPersonCart() {
        return Cart.allPersonCart;
    }

}