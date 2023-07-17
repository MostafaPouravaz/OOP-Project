package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommentForRestaurant {

    private static ArrayList<CommentForRestaurant> allComments = new ArrayList<>();
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
        saveRestaurantCommentToFile();
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
        saveRestaurantCommentToFile();
    }

    public CommentForRestaurant(int restaurantID, int customerID, String comment) {
        this.RestaurantID = restaurantID;
        this.customerID = customerID;
        this.Comment = comment;
        this.commentID = ++counterID;
        addComment(this);
    }
    public static CommentForRestaurant getCommentByRestaurantIDAndCostumerID(int restaurantID, int costumer_ID) {
        if (loadRestaurantCommentFromFile() != null)
            allComments = new ArrayList<>(loadRestaurantCommentFromFile());
        for (CommentForRestaurant allComment : allComments) {
            if (allComment.getCustomerID() == costumer_ID && allComment.getRestaurantID() == restaurantID)
                return allComment;
        }
        return null;
    }
    public static CommentForRestaurant getCommentByCommentID(int commentID) {
        if (loadRestaurantCommentFromFile() != null)
            allComments = new ArrayList<>(loadRestaurantCommentFromFile());
        return allComments.get(commentID-1);
    }
    private void addComment(CommentForRestaurant commentForRestaurant) {
        if (loadRestaurantCommentFromFile() != null)
            allComments = new ArrayList<>(loadRestaurantCommentFromFile());
        allComments.add(commentForRestaurant);
        saveRestaurantCommentToFile();
    }
    public static void saveRestaurantCommentToFile(){
        try {
            FileWriter fileWriterRestaurantComment = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\restaurantComments.json");
            Gson gson = new Gson();
            gson.toJson(allComments, fileWriterRestaurantComment);
            fileWriterRestaurantComment.close();
        } catch (IOException e) {
            System.out.println("problem in writing");
        }
    }
    public static ArrayList<CommentForRestaurant> loadRestaurantCommentFromFile(){
        try {
            FileReader fileReaderRestaurantComment = null;
            fileReaderRestaurantComment = new FileReader("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\restaurantComment.json");
            Type type = new TypeToken<ArrayList<CommentForRestaurant>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<CommentForRestaurant> allC = new ArrayList<>();
            allC = gson.fromJson(fileReaderRestaurantComment,type);
            fileReaderRestaurantComment.close();
            allComments = new ArrayList<>();
            if (allC != null)
                allComments.addAll(allC);
            counterID = allComments.size();
        } catch (IOException e) {
            System.out.println("problem in reading");
        }
        return allComments;
    }
}
