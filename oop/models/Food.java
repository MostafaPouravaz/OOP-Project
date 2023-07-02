package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
//edit setter getter
//comment
public class Food {
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private int finalRate;
    private String name;
    private final static ArrayList<Food> allFoods = new ArrayList<>();
    private int ID;
    private static int IDCounter=0;
    private int price;
    private int ID_restaurant;
    LocalDateTime startTime;
    int period;
    int foodTypeID;
    private int discount;
    private boolean active = false;
    private boolean isDiscounted = false;
    public void discounter (int timePeriod){
        this.startTime = LocalDateTime.now();
        this.period = timePeriod;
        this.isDiscounted = true;
    }
    public boolean discountActive(){
        if (!isDiscounted)
            return false;
        return LocalDateTime.now().isBefore(startTime.plusSeconds(this.period));
    }//for identifying that had discount or no or time is spent.

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

    public int getPrice() {
        if (discountActive())
            return price*(100-getDiscount())/100;
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getID_restaurant() {
        return ID_restaurant;
    }

    public void setID_restaurant(int ID_restaurant) {
        this.ID_restaurant = ID_restaurant;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isActive() {
        return active;
    }

    public int getFoodTypeID() {
        return foodTypeID;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFinalRate() {
        finalRate = 0;
        for (Rating rating : ratings) finalRate += rating.getRate();
        return finalRate/ratings.size();
    }
    public void ShowComments() {
        for (int i=0; comments.size()>i; i++){
            System.out.println(i+1 + ". \n" + Customer.getUserByUserID(comments.get(i).getCustomerID()).getUsername() + " :" + comments.get(i).getComment());
            if (comments.get(i).isResponseExists())
                System.out.println("manager's response : " + comments.get(i).getResponse());
        }
    }
    public void addRate(int customerID, int rate){
        ratings.add(new Rating(ID, customerID, rate));
    }
    public void addComment(int customerID, String comment){
        comments.add(new Comment(ID, customerID, comment));
    }
    public void editRate(int customerID, int rate) {
        for (Rating rating : ratings) {
            if (rating.getCustomerID() == customerID)
                rating.editRate(rate);
        }
        Rating.getRatingByFoodIDAndCostumerID(ID,customerID).editRate(rate);
    }
    public void editComment(int customerID, String comment) {
        for (Comment comment1 : comments) {
            if (comment1.getCustomerID() == customerID)
                comment1.editComment(comment);
        }
        Comment.getCommentByFoodIDAndCostumerID(ID,customerID).editComment(comment);
    }
    public void addOrEditResponse(int commentID, String response) {
        for (Comment comment1 : comments) {
            if(comment1.getCommentID()==commentID)
                comment1.setResponse(commentID , response);
        }
    }
    public Food(String name, int price, int ID_restaurant, int foodTypeID) {
        this.name = name;
        this.price = price;
        this.ID_restaurant = ID_restaurant;
        this.foodTypeID = foodTypeID;
        this.active =true;
        this.ID = ++IDCounter;
        Food.allFoods.add(this);
    }
}
