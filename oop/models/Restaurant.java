package models;

import enums.FoodType;

import java.util.ArrayList;


public class Restaurant {
    private final static ArrayList<Restaurant> allRestaurant = new ArrayList<>();

    private ArrayList<RatingForRestaurant> allRatings = new ArrayList<>();

    private ArrayList<CommentForRestaurant> allComments = new ArrayList<>();
    private String name;
    int ID_Counter=0;
    private int ID;
    private int finalRate;

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
    public void editFoodType(int ID, FoodType foodType) {
        this.foodType.set(ID-1,FoodType.getIntFromFoodType(foodType));
    }

    public ArrayList<Food> getFood() {
        return food;
    }

    public ArrayList<RatingForRestaurant> getAllRatings() {
        return allRatings;
    }

    public ArrayList<CommentForRestaurant> getAllComments() {
        return allComments;
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

    public void showComments() {
        for (int i = 0; allComments.size()>i; i++){
            System.out.println(i+1 + ". \n" +
                    Customer.getUserByUserID(allComments.get(i).getCustomerID()).getUsername() + " :"
                    + allComments.get(i).getComment());
            if (allComments.get(i).isResponseExists())
                System.out.println("manager's response : " + allComments.get(i).getResponse());
        }
    }
    public void addComment(int customerID, String comment){
        allComments.add(new CommentForRestaurant(ID, customerID, comment));
    }
    public void editComment(int customerID, String comment) {
        for (CommentForRestaurant comment1 : allComments) {
            if (comment1.getCustomerID() == customerID)
                comment1.editComment(comment);
        }
        CommentForRestaurant.getCommentByRestaurantIDAndCostumerID(ID,customerID).editComment(comment);
    }
    public void addOrEditResponse(int commentID, String response) {
        for (CommentForRestaurant comment1 : allComments) {
            if(comment1.getCommentID()==commentID)
                comment1.setResponse(commentID , response);
        }
    }
    public int getFinalRate() {
        finalRate = 0;
        for (RatingForRestaurant rating : allRatings) finalRate += rating.getRate();
        return finalRate/ allRatings.size();
    }

    public void addRate(int customerID, double rate){
        allRatings.add(new RatingForRestaurant(ID, customerID, rate));
    }
    public void editRate(int customerID, double rate) {
        for (RatingForRestaurant rating : allRatings) {
            if (rating.getCustomerID() == customerID)
                rating.editRate(rate);
        }
        RatingForRestaurant.getRatingByRestaurantIDAndCostumerID(ID,customerID).editRate(rate);
    }
}
