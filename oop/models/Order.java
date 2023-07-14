package models;

import java.time.LocalDateTime;
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
    private int destinationNode;
    private int estimatedTime;
    private LocalDateTime startTime;
    private boolean isDelivered;
    private String status;

    public int getDestinationNode() {
        return destinationNode;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

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
    private int finalPrice = 0;
    //private int offPercent;
    private static int counterID=0;
    private final static ArrayList<Order> allOrders = new ArrayList<>(); //History

    public static ArrayList<Order> getAllOrders() {
        return Order.allOrders;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Order(Cart cart,int estimatedTime, LocalDateTime startTime, int destinationNode){
        for (Food orderedFood : orderedFoods) this.finalPrice += orderedFood.getPrice();
        this.customerID = cart.getCustomerID();
        this.restaurantName = Restaurant.getRestaurantByRestaurantID(cart.getRestaurantID()).getName();
        this.orderedFoods = cart.getChosenFoods();
        this.orderID = ++counterID;
        this.destinationNode = destinationNode;
        this.startTime = startTime;
        this.estimatedTime = estimatedTime;
        this.status = "preparing";
        allOrders.add(this);
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public boolean isDelivered() {
        isDelivered = LocalDateTime.now().isAfter(startTime.plusSeconds(estimatedTime));
        setDelivered(isDelivered);
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
    public ArrayList<Order> pastOrders(){
        ArrayList<Order> pO = new ArrayList<>();
        for (Order allOrder : allOrders) {
            if (allOrder.isDelivered())
                pO.add(allOrder);
        }
        return pO;
    }
    public ArrayList<Order> openOrders(){
        ArrayList<Order> pO = new ArrayList<>();
        for (Order allOrder : allOrders) {
            if (!allOrder.isDelivered())
                pO.add(allOrder);
        }
        return pO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
