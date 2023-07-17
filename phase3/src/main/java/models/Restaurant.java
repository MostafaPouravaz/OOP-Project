package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.FoodType;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Restaurant {
    private static ArrayList<Restaurant> allRestaurant = new ArrayList<>();

    private static ArrayList<RatingForRestaurant> allRatings = new ArrayList<>();

    private static ArrayList<CommentForRestaurant> allComments = new ArrayList<>();
    private static ArrayList<Integer> foodTypes = new ArrayList<>();
    private static ArrayList<Food> foods = new ArrayList<>();
    private String name;
    static int ID_Counter = 0;
    private int RestaurantID;
    private int finalRate;

    private int ID_Owner;
    private int locationNode;


    public Restaurant(String name, int ID_Owner, int locationNode) {
        RestaurantID = ++ID_Counter;
        this.name = name;
        this.ID_Owner = ID_Owner;
        this.locationNode = locationNode;
        addRestaurant(this);
    }

    private void addRestaurant(Restaurant restaurant) {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        allRestaurant.add(restaurant);
        saveRestaurantToFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        saveRestaurantToFile();
    }

    public int getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.RestaurantID = restaurantID;
        saveRestaurantToFile();
    }

    public int getID_Owner() {
        return ID_Owner;
    }

    public void setID_Owner(int ID_Owner) {
        this.ID_Owner = ID_Owner;
        saveRestaurantToFile();
    }

    public int getLocationNode() {
        return locationNode;
    }

    public void setLocationNode(int locationNode) {
        this.locationNode = locationNode;
        saveRestaurantToFile();
    }

    public ArrayList<Integer> getFoodTypes() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        return foodTypes;
    }

    public FoodType getFoodType(int ID) {
        return FoodType.getFoodTypeFromInt(ID);
    }

    public void setFoodType(int ID) {
        foodTypes.add(ID);
        saveRestaurantToFile();
    }

    public void editFoodType(int ID, FoodType foodType) {
        foodTypes.set(ID, FoodType.getIntFromFoodType(foodType));
        saveRestaurantToFile();
    }

    public ArrayList<Food> getFoods() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        return foods;
    }

    public ArrayList<RatingForRestaurant> getAllRatings() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        return allRatings;
    }

    public ArrayList<CommentForRestaurant> getAllComments() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        return allComments;
    }

    public void setFoods(Food foods) {
        this.foods.add(foods);
        saveRestaurantToFile();
    }

    public static ArrayList<Restaurant> getAllRestaurant() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        return Restaurant.allRestaurant;
    }

    public static ArrayList<Restaurant> getVendorsRestaurant(int vendorID) {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        ArrayList<Restaurant> vendorRestaurants = new ArrayList<>();
        for (Restaurant restaurant : allRestaurant) {
            if (restaurant.getID_Owner() == vendorID)
                vendorRestaurants.add(restaurant);
        }
        return vendorRestaurants;
    }

    public static Restaurant getRestaurantByRestaurantName(String name) {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        for (Restaurant restaurant : Restaurant.allRestaurant) {
            if (restaurant.name.equals(name)) {
                return restaurant;
            }
        }
        return null;
    }

    public static Restaurant getRestaurantByRestaurantID(int ID) {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        if (ID <= allRestaurant.size())
            return allRestaurant.get(ID - 1);
        return null;
    }

    public void showComments() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        for (int i = 0; allComments.size() > i; i++) {
            System.out.println(i + 1 + ". \n" +
                    Customer.getUserByUserID(allComments.get(i).getCustomerID()).getUsername() + " :"
                    + allComments.get(i).getComment());
            if (allComments.get(i).isResponseExists())
                System.out.println("manager's response : " + allComments.get(i).getResponse());
        }
    }

    public void addComment(int customerID, String comment) {
        allComments.add(new CommentForRestaurant(RestaurantID, customerID, comment));
        saveRestaurantToFile();
    }

    public void editComment(int customerID, String comment) {
        for (CommentForRestaurant comment1 : allComments) {
            if (comment1.getCustomerID() == customerID)
                comment1.editComment(comment);
        }
        if (CommentForRestaurant.getCommentByRestaurantIDAndCostumerID(RestaurantID, customerID) != null)
            CommentForRestaurant.getCommentByRestaurantIDAndCostumerID(RestaurantID, customerID).editComment(comment);
        saveRestaurantToFile();
    }

    public void addOrEditResponse(int commentID, String response) {
        for (CommentForRestaurant comment1 : allComments) {
            if (comment1.getCommentID() == commentID)
                comment1.setResponse(commentID, response);
        }
        saveRestaurantToFile();
    }

    public int getFinalRate() {
        if (loadRestaurantFromFile() != null)
            allRestaurant = new ArrayList<>(loadRestaurantFromFile());
        finalRate = 0;
        if (allRatings == null)
            return -1;
        for (RatingForRestaurant rating : allRatings) finalRate += rating.getRate();
        return finalRate / allRatings.size();
    }

    public void addRate(int customerID, double rate) {
        allRatings.add(new RatingForRestaurant(RestaurantID, customerID, rate));
        saveRestaurantToFile();
    }

    public void editRate(int customerID, double rate) {
        for (RatingForRestaurant rating : allRatings) {
            if (rating.getCustomerID() == customerID)
                rating.editRate(rate);
        }
        if (RatingForRestaurant.getRatingByRestaurantIDAndCostumerID(RestaurantID, customerID) != null)
            RatingForRestaurant.getRatingByRestaurantIDAndCostumerID(RestaurantID, customerID).editRate(rate);
        saveRestaurantToFile();
    }

    public static void saveRestaurantToFile() {
        try {
            FileWriter fileWriterRestaurant = new FileWriter("oop\\files\\restaurants.json");
            Gson gson = new Gson();
            gson.toJson(allRestaurant, fileWriterRestaurant);
            fileWriterRestaurant.close();
        } catch (IOException e) {
            System.out.println("problem in writing");
        }
    }
    public ArrayList<Food> getFoodsWithFoodTypeInt(int foodTypeInt){
        ArrayList<Food> foodss = new ArrayList<>();
        for(int i=0 ; i< foods.size() ; i++)
            if(foods.get(i).getFoodTypeID()==foodTypeInt)
                foodss.add(foods.get(i));

        return foodss;
    }
    public Food getFoodWithName(String name){
        for(int i=0 ; i<foods.size() ; i++)
            if(foods.get(i).getName().equals(name))
                return foods.get(i);
        return null;
    }
    public static Restaurant getRestaurantByRestaurantName(String name , int ID) {
        for (Restaurant restaurant : Restaurant.allRestaurant) {
            if (restaurant.name.equals(name) && restaurant.getID_Owner()==ID) {
                return restaurant;
            }

        }
        return null;
    }

    public static ArrayList<Restaurant> loadRestaurantFromFile() {
        try {
            FileReader fileReaderRestaurant = null;
            fileReaderRestaurant = new FileReader("oop\\files\\restaurants.json");
            Type type = new TypeToken<ArrayList<Restaurant>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<Restaurant> allR = new ArrayList<>();
            allR = gson.fromJson(fileReaderRestaurant, type);
            fileReaderRestaurant.close();
            allRestaurant = new ArrayList<>();
            if (allR != null)
                allRestaurant.addAll(allR);
            ID_Counter = allRestaurant.size();
        } catch (IOException e) {
            System.out.println("problem in reading");
        }
        return allRestaurant;
    }
}


