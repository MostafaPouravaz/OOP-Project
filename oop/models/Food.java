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
//edit setter getter
//comment
public class Food {

    private static ArrayList<RatingForFood> allRatings = new ArrayList<>();
    private static ArrayList<CommentForFood> allComments = new ArrayList<>();
    private static ArrayList<Food> allFoods = new ArrayList<>();
    private int finalRate;
    private String name;
    private int ID;
    private static int IDCounter = 0;
    private int price;
    private int ID_restaurant;
    LocalDateTime startTime;
    int period;
    int foodTypeID;
    private int discount;
    private boolean active = false;
    public Food(String name, int price, int ID_restaurant, int foodTypeID) {
        this.name = name;
        this.price = price;
        this.ID_restaurant = ID_restaurant;
        this.foodTypeID = foodTypeID;
        this.active = true;
        this.ID = ++IDCounter;
        addFood(this);
    }
    public ArrayList<RatingForFood> getRatings() {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        allRatings = RatingForFood.getAllRatingsByFID(ID);
        return allRatings;
    }//

    public ArrayList<CommentForFood> getComments() {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        allComments = CommentForFood.getAllCommentsByFID(getID());
        return allComments;
    }

    public static ArrayList<Food> foodsSort(int ID){
        ArrayList<Food> foods = new ArrayList<>();
        for(int i = 0 ; i< allFoods.size() ; i++)
            if(allFoods.get(i).getID_restaurant() == ID)
                foods.add(allFoods.get(i));

        return foods;
    }

    public static ArrayList<Food> getAllRestaurantFoods(int restaurantID) {
        if (loadFoodFromFile().size() != 0)
            allFoods = new ArrayList<>(loadFoodFromFile());
        ArrayList<Food> foods = new ArrayList<>();
        for (Food allFood : allFoods) {
            if (allFood.getID_restaurant() == restaurantID)
                foods.add(allFood);
        }
        return foods;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getPeriod() {
        return period;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }
//    public static ArrayList<Food> getAllRestaurantFoods(int restaurantID) {
//        if (loadFoodFromFile().size() != 0)
//            allFoods = new ArrayList<>(loadFoodFromFile());
//        ArrayList<Food> foods = new ArrayList<>();
//        for (Food allFood : allFoods) {
//            if (allFood.getID_restaurant() == restaurantID)
//                foods.add(allFood);
//        }
//        return foods;
//    }
    public static ArrayList<Food> getAllFoods() {
        if (loadFoodFromFile().size() != 0)
            allFoods = new ArrayList<>(loadFoodFromFile());
        return Food.allFoods;
    }

    private boolean isDiscounted = false;

    public void discounter(int timePeriod) {
        this.startTime = LocalDateTime.now();
        this.period = timePeriod * 60;
        this.isDiscounted = true;
        saveFoodToFile();
    }

    public boolean discountActive() {
        if (!isDiscounted)
            return false;
        return LocalDateTime.now().isBefore(startTime.plusSeconds(this.period));
    }//for identifying that had discount or no or time is spent.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        saveFoodToFile();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        saveFoodToFile();
    }

    public int getPrice() {
        if (discountActive())
            return price * (100 - getDiscount()) / 100;
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        saveFoodToFile();
    }

    public int getID_restaurant() {
        return ID_restaurant;
    }

    public void setID_restaurant(int ID_restaurant) {
        this.ID_restaurant = ID_restaurant;
        saveFoodToFile();
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
        saveFoodToFile();
    }

    public boolean isActive() {
        return active;
    }

    public int getFoodTypeID() {
        return foodTypeID;
    }

    public void setActive(boolean active) {
        this.active = active;
        saveFoodToFile();
    }

    public int getFinalRate() {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        finalRate = 0;
        if (allRatings == null)
            return -1;
        else {
            for (RatingForFood rating : allRatings) finalRate += rating.getRate();
            return finalRate / allRatings.size();
        }
    }

    public void showComments() {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        for (int i = 0; allComments.size() > i; i++) {
            System.out.println(i + 1 + ". \n" +
                    Customer.getUserByUserID(allComments.get(i).getCustomerID()).getUsername() + " :"
                    + allComments.get(i).getComment());
            if (allComments.get(i).isResponseExists())
                System.out.println("manager's response : " + allComments.get(i).getResponse());
        }
    }

    public void addRate(int customerID, double rate) {
        allRatings.add(new RatingForFood(ID, customerID, rate));
        saveFoodToFile();
    }

    public void addComment(int customerID, String comment) {
        allComments.add(new CommentForFood(ID, customerID, comment));
        saveFoodToFile();
    }

    public void editRate(int customerID, double rate) {
        for (RatingForFood rating : allRatings) {
            if (rating.getCustomerID() == customerID)
                rating.editRate(rate);
        }
        if (RatingForFood.getRatingByFoodIDAndCostumerID(ID, customerID) != null)
            RatingForFood.getRatingByFoodIDAndCostumerID(ID, customerID).editRate(rate);
        saveFoodToFile();
    }

    public void editComment(int customerID, String comment) {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        for (CommentForFood comment1 : allComments) {
            if (comment1.getCustomerID() == customerID) {
                comment1.editComment(comment);
                if (CommentForFood.getCommentByFoodIDAndCostumerID(ID, customerID) != null)
                    CommentForFood.getCommentByFoodIDAndCostumerID(ID, customerID).editComment(comment);
                break;
            }
        }
    }

    public void addOrEditResponse(int commentID, String response) {
        for (CommentForFood comment1 : allComments) {
            if (comment1.getCommentID() == commentID)
                comment1.setResponse(commentID, response);
        }
        saveFoodToFile();
    }

    private void addFood(Food food) {
        if (loadFoodFromFile() != null)
            allFoods = new ArrayList<>(loadFoodFromFile());
        allFoods.add(food);
        saveFoodToFile();
    }

    public static void saveFoodToFile() {
        try {
            FileWriter fileWriterFood = new FileWriter("oop\\files\\foods.json");
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
            gson.toJson(allFoods, fileWriterFood);
            fileWriterFood.close();
        } catch (IOException e) {
            System.out.println(" ");
        }
    }

    public static ArrayList<Food> loadFoodFromFile() {
        try {
            FileReader fileReaderFood = null;
            fileReaderFood = new FileReader("oop\\files\\foods.json");
            Type type = new TypeToken<ArrayList<Food>>() {
            }.getType();
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
            ArrayList<Food> allF = new ArrayList<>();
            allF = gson.fromJson(fileReaderFood, type);
            fileReaderFood.close();
            allFoods = new ArrayList<>();
            if (allF != null)
                allFoods.addAll(allF);
            IDCounter = allFoods.size();
        } catch (IOException e) {
            System.out.println(" ");
        }
        return allFoods;
    }
}

//    public static void saveRatingForFoodToFile(){
//        try {
//            FileWriter fileWriterRatingForFood = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\ratingForFood.json");
//            Gson gson = new Gson();
//            gson.toJson(allRatings, fileWriterRatingForFood);
//            fileWriterRatingForFood.close();
//        } catch (IOException e) {
//            System.out.println("problem in writing");
//        }
//    }
//    public static ArrayList<RatingForFood> loadRatingForFoodFromFile(){
//        try {
//            FileReader fileReaderRatingForFood = null;
//            fileReaderRatingForFood = new FileReader("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\ratingForFood.json");
//            Type type = new TypeToken<ArrayList<RatingForFood>>(){}.getType();
//            Gson gson = new Gson();
//            ArrayList<RatingForFood> allR = new ArrayList<>();
//            allR = gson.fromJson(fileReaderRatingForFood,type);
//            fileReaderRatingForFood.close();
//            allRatings = new ArrayList<>();
//            if (allR != null)
//                allRatings.addAll(allR);
//        } catch (IOException e) {
//            System.out.println("problem in reading");
//        }
//        return allRatings;
//    }
//    public static void saveCommentForFoodToFile(){
//        try {
//            FileWriter fileWriterCommentForFood = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\commentForFood.json");
//            Gson gson = new Gson();
//            gson.toJson(allComments, fileWriterCommentForFood);
//            fileWriterCommentForFood.close();
//        } catch (IOException e) {
//            System.out.println("problem in writing");
//        }
//    }
//    public static ArrayList<CommentForFood> loadCommentForFoodFromFile(){
//        try {
//            FileReader fileReaderCommentForFood = null;
//            fileReaderCommentForFood = new FileReader("C:\\Users\\Mostafa\\IdeaProjects\\OOP-Project1\\oop\\files\\commentForFood.json");
//            Type type = new TypeToken<ArrayList<CommentForFood>>(){}.getType();
//            Gson gson = new Gson();
//            ArrayList<CommentForFood> allC = new ArrayList<>();
//            allC = gson.fromJson(fileReaderCommentForFood,type);
//            fileReaderCommentForFood.close();
//            allComments = new ArrayList<>();
//            if (allC != null)
//                allComments.addAll(allC);
//        } catch (IOException e) {
//            System.out.println("problem in reading");
//        }
//        return allComments;
//    }

