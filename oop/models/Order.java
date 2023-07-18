package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Order {
    //arraylist from current orders
    private String restaurantName;
    private static ArrayList<Food> orderedFoods = new ArrayList<>();
    private static ArrayList<Order> allOrders = new ArrayList<>(); //History

    public ArrayList<Food> getOrderedFoods() {
        if (loadOrderFromFile() != null)
            allOrders = new ArrayList<>(loadOrderFromFile());
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
    private static int counterID = 0;

    public static ArrayList<Order> getAllOrders() {
        if (loadOrderFromFile() != null)
            allOrders = new ArrayList<>(loadOrderFromFile());
        return Order.allOrders;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Order() {
    }

    public Order(Cart cart, int estimatedTime, LocalDateTime startTime, int destinationNode) {
        this.orderedFoods = cart.getChosenFoods();
        for (Food orderedFood : orderedFoods) this.finalPrice += orderedFood.getPrice();
        this.customerID = cart.getCustomerID();
        this.restaurantName = Objects.requireNonNull(Restaurant.getRestaurantByRestaurantID(cart.getRestaurantID())).getName();
        this.orderID = ++counterID;
        this.destinationNode = destinationNode;
        this.startTime = startTime;
        this.estimatedTime = estimatedTime;
        this.status = "preparing";
        addOrder(this);
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
        saveOrderToFile();
    }

    public boolean isDelivered() {
        isDelivered = LocalDateTime.now().isAfter(startTime.plusSeconds(estimatedTime));
        setDelivered(isDelivered);
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
        saveOrderToFile();
    }

    public ArrayList<Order> pastOrders() {
        if (loadOrderFromFile() != null)
            allOrders = new ArrayList<>(loadOrderFromFile());
        ArrayList<Order> pO = new ArrayList<>();
        for (Order allOrder : allOrders) {
            if (allOrder.isDelivered())
                pO.add(allOrder);
        }
        return pO;
    }

    public ArrayList<Order> openOrders() {
        if (loadOrderFromFile() != null)
            allOrders = new ArrayList<>(loadOrderFromFile());
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
        saveOrderToFile();
    }

    private void addOrder(Order order) {
        if (loadOrderFromFile() != null)
            allOrders = new ArrayList<>(loadOrderFromFile());
        allOrders.add(order);
        saveOrderToFile();
    }

    public static void saveOrderToFile() {
        try {
            FileWriter fileWriterOrder = new FileWriter("oop\\files\\orders.json");
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter jsonWriter, LocalDateTime localDate) throws IOException{
                    if (localDate == null)
                        jsonWriter.value(LocalDateTime.now().toString());
                    else
                        jsonWriter.value(localDate.toString());
                }
                @Override
                public LocalDateTime read(JsonReader jsonReader)throws IOException{
                    return LocalDateTime.parse(jsonReader.nextString());
                }
            }).create();
            gson.toJson(allOrders, fileWriterOrder);
            fileWriterOrder.close();
        } catch (IOException e) {
            System.out.println(" ");
        }
    }

    public static ArrayList<Order> loadOrderFromFile() {
        try {
            FileReader fileReaderOrder = null;
            fileReaderOrder = new FileReader("oop\\files\\orders.json");
            Type type = new TypeToken<ArrayList<Order>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter jsonWriter, LocalDateTime localDate) throws IOException{
                    jsonWriter.value(localDate.toString());
                }
                @Override
                public LocalDateTime read(JsonReader jsonReader)throws IOException{
                    return LocalDateTime.parse(jsonReader.nextString());
                }
            }).create();
            ArrayList<Order> allO = new ArrayList<>();
            allO = gson.fromJson(fileReaderOrder, type);
            fileReaderOrder.close();
            allOrders = new ArrayList<>();
            if (allO != null)
                allOrders.addAll(allO);
            counterID = allOrders.size();
        } catch (IOException e) {
            System.out.println(" ");
        }
        return allOrders;
    }
}
//    public static void saveOrderFoodsToFile(){
//        try {
//            FileWriter fileWriterOrderFoods = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\orderFoods.json");
//            Gson gson = new Gson();
//            gson.toJson(orderedFoods, fileWriterOrderFoods);
//            fileWriterOrderFoods.close();
//        } catch (IOException e) {
//            System.out.println("problem in writing");
//        }
//    }
//    public static ArrayList<Food> loadOrderFoodsFromFile(){
//        try {
//            FileReader fileReaderOrderFoods = null;
//            fileReaderOrderFoods = new FileReader("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\orderFoods.json");
//            Type type = new TypeToken<ArrayList<Food>>(){}.getType();
//            Gson gson = new Gson();
//            ArrayList<Food> allO = new ArrayList<>();
//            allO = gson.fromJson(fileReaderOrderFoods,type);
//            fileReaderOrderFoods.close();
//            orderedFoods = new ArrayList<>();
//            if (allO != null)
//                orderedFoods.addAll(allO);
//        } catch (IOException e) {
//            System.out.println("problem in reading");
//        }
//        return orderedFoods;
//    }

