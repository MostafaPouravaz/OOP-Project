package models;

import java.util.ArrayList;

public class Comment {
    private final static ArrayList<Comment> allComments = new ArrayList<Comment>();
    private int foodID;
    private int customerID;
    private String Comment;
    private int counterID=0;
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

    public int getFoodID() {
        return foodID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getComment() {
        return Comment;
    }

    public void editComment(String comment) {
        this.Comment = comment;
    }

    public Comment(int foodID, int customerID, String comment) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.Comment = comment;
        this.commentID = ++counterID;
        allComments.add(this);
    }
    public static Comment getCommentByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        for (Comment allComment : allComments) {
            if (allComment.getCustomerID() == costumer_ID && allComment.getFoodID() == foodID)
                return allComment;
        }
        return null;
    }
    public static Comment getCommentByCommentID(int commentID) {
        return allComments.get(commentID-1);
    }
}
