package models;

import java.util.ArrayList;

public class Order {
    //arraylist from current orders
    private String restaurantName;
    private ArrayList<Food> orderedFoods = new ArrayList<>();

    public ArrayList<Food> getOrderedFoods() {
        return orderedFoods;
    }

    private int orderID;
    private int customerID;

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public static int getCounterID() {
        return counterID;
    }

    private int finalPrice;
    //private int offPercent;
    private static int counterID=0;
    private final static ArrayList<Order> allOrders = new ArrayList<>(); //History

    public static ArrayList<Order> getAllOrders() {
        return Order.allOrders;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Order(Cart cart){
        this.restaurantName = Restaurant.getRestaurantByRestaurantID(cart.getRestaurantID()).getName();
        this.orderedFoods = cart.getChosenFoods();
        this.customerID = cart.getCustomerID();
        this.orderID=++counterID;
        this.finalPrice =0;
        for (Food orderedFood : orderedFoods) this.finalPrice += orderedFood.getPrice();
        allOrders.add(this);
    }
}
