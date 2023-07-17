package models;

import java.util.ArrayList;

public class CommentForRestaurant {

    private final static ArrayList<CommentForRestaurant> allComments = new ArrayList<>();
    private int RestaurantID;
    private int customerID;
    private String Comment;
    private static int counterID=0;
    private int commentID;
    private  String response;
    private boolean responseExists = false;

    public String getResponse() {
        return response;
    }

    public void setResponse(int commentID,String response) {
        this.response = response;
        allComments.get(commentID-1).response = response;
    }


    public int getCommentID() {
        return commentID;
    }

    public boolean isResponseExists() {
        return responseExists;
    }

    public int getRestaurantID() {
        return RestaurantID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getComment() {
        return Comment;
    }

    public void editComment(String comment) {
        this.Comment = comment;
        allComments.get(this.getCommentID()-1).Comment = comment;
    }

    public CommentForRestaurant(int restaurantID, int customerID, String comment) {
        this.RestaurantID = restaurantID;
        this.customerID = customerID;
        this.Comment = comment;
        this.commentID = ++counterID;
        allComments.add(this);
    }
    public static CommentForRestaurant getCommentByRestaurantIDAndCostumerID(int restaurantID, int costumer_ID) {
        for (CommentForRestaurant allComment : allComments) {
            if (allComment.getCustomerID() == costumer_ID && allComment.getRestaurantID() == restaurantID)
                return allComment;
        }
        return null;
    }
    public static CommentForRestaurant getCommentByCommentID(int commentID) {
        return allComments.get(commentID-1);
    }

}
