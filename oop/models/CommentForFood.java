package models;

import java.util.ArrayList;

public class CommentForFood {
    private final static ArrayList<CommentForFood> allComments = new ArrayList<>();
    private int foodID;
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
        allComments.get(this.getCommentID()-1).Comment = comment;
    }

    public CommentForFood(int foodID, int customerID, String comment) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.Comment = comment;
        this.commentID = ++counterID;
        allComments.add(this);
    }
    public static CommentForFood getCommentByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        for (CommentForFood allComment : allComments) {
            if (allComment.getCustomerID() == costumer_ID && allComment.getFoodID() == foodID)
                return allComment;
        }
        return null;
    }
    public static CommentForFood getCommentByCommentID(int commentID) {
        return allComments.get(commentID-1);
    }
}
