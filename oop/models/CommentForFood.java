package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommentForFood {
    private static ArrayList<CommentForFood> allComments = new ArrayList<>();
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
        allComments.get(commentID).response = response;
        saveFoodCommentToFile();
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
        allComments.get(this.getCommentID()).Comment = comment;
        saveFoodCommentToFile();
    }

    public CommentForFood(int foodID, int customerID, String comment) {
        this.foodID = foodID;
        this.customerID = customerID;
        this.Comment = comment;
        this.commentID = ++counterID;
        addComment(this);
    }
    public static CommentForFood getCommentByFoodIDAndCostumerID(int foodID , int costumer_ID) {
        if (loadFoodCommentFromFile() != null)
            allComments = new ArrayList<>(loadFoodCommentFromFile());
        for (CommentForFood allComment : allComments) {
            if (allComment.getCustomerID() == costumer_ID && allComment.getFoodID() == foodID)
                return allComment;
        }
        return null;
    }
    public static ArrayList<CommentForFood> getAllCommentsByFID(int foodID) {
        ArrayList<CommentForFood> allCommentsR = new ArrayList<>();
        if (loadFoodCommentFromFile() != null)
            allComments = new ArrayList<>(loadFoodCommentFromFile());

        for (CommentForFood allComment : allComments) {
            if (allComment.getFoodID() == foodID)
                allCommentsR.add(allComment);
        }
        return allCommentsR;
    }
    public static CommentForFood getCommentByCommentID(int commentID) {
        if (loadFoodCommentFromFile() != null)
            allComments = new ArrayList<>(loadFoodCommentFromFile());
        return allComments.get(commentID);
    }
    private void addComment(CommentForFood commentForFood) {
        if (loadFoodCommentFromFile() != null)
            allComments = new ArrayList<>(loadFoodCommentFromFile());
        allComments.add(commentForFood);
        saveFoodCommentToFile();
    }
    public static void saveFoodCommentToFile(){
        try {
            FileWriter fileWriterFoodComment = new FileWriter("java\\files\\foodComment.json");
            Gson gson = new Gson();
            gson.toJson(allComments, fileWriterFoodComment);
            fileWriterFoodComment.close();
        } catch (IOException e) {
            System.out.println(" ");
        }
    }
    public static ArrayList<CommentForFood> loadFoodCommentFromFile(){
        try {
            FileReader fileReaderFoodComment = null;
            fileReaderFoodComment = new FileReader("java\\files\\foodComment.json");
            Type type = new TypeToken<ArrayList<CommentForFood>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<CommentForFood> allC = new ArrayList<>();
            allC = gson.fromJson(fileReaderFoodComment,type);
            fileReaderFoodComment.close();
            allComments = new ArrayList<>();
            if (allC != null)
                allComments.addAll(allC);
            counterID = allComments.size();
        } catch (IOException e) {
            System.out.println(" ");
        }
        return allComments;
    }
}
