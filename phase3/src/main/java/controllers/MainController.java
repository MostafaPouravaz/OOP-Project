package controllers;

import java.util.ArrayList;

import enums.FoodType;
import enums.Message;
import models.*;
import views.MainMenu;
import views.Menu;

public class MainController extends Controller{
    private static MainController instance = null;

    private MainController() {

    }

    private static void setInstance(MainController instance) {
        MainController.instance = instance;
    }

    public static MainController getInstance() {
        if (MainController.instance == null) {
            MainController.setInstance(new MainController());
        }

        return MainController.instance;
    }

    public ArrayList<Restaurant> handleShowRestaurants() {
        return Restaurant.getAllRestaurant();
    }
    public ArrayList<Food> handleShowFoods() {
        return MainMenu.getCurrentRestaurant().getFoods();
    }
    public ArrayList<Restaurant> handleSearchRestaurants(String choice) {
        ArrayList <Restaurant> searchedRestaurants = new ArrayList<>();
        for(int i=0 ; i<Restaurant.getAllRestaurant().size() ; i++)
            if(Restaurant.getAllRestaurant().get(i).getName().contains(choice))
                searchedRestaurants.add(Restaurant.getAllRestaurant().get(i));
        return searchedRestaurants;
    }

    public ArrayList<Food> handleSearchFoods(String choice) {
        ArrayList <Food> searchedFoods = new ArrayList<>();
        for(int i=0 ; i<MainMenu.getCurrentRestaurant().getFoods().size() ; i++)
            if(MainMenu.getCurrentRestaurant().getFoods().get(i).getName().contains(choice))
                searchedFoods.add(MainMenu.getCurrentRestaurant().getFoods().get(i));
        return searchedFoods;
    }


    public Restaurant handleChooseRestaurant(String choice) {
        int i = Integer.parseInt(choice.trim())-1;
        if (i<Restaurant.getAllRestaurant().size())
            return Restaurant.getAllRestaurant().get(i);
        else
            return null;
    }

    public Food handleChooseFood(String choice) {
        int i = Integer.parseInt(choice.trim())-1;
        if (i<MainMenu.getCurrentRestaurant().getFoods().size())
            return MainMenu.getCurrentRestaurant().getFoods().get(i);
        else
            return null;
    }
    public Message handleAddRestaurant(String name, int locationNode) {
        User loggedInUser = Menu.getLoggedInUser();
        MainMenu.setCurrentRestaurant(new Restaurant(name, loggedInUser.getUserId(),locationNode));
        return Message.SUCCESS;
    }
    public Message handleAddFood(String name, int price, int foodType) {
        User loggedInUser = Menu.getLoggedInUser();
        Restaurant currentRestaurant = MainMenu.getCurrentRestaurant();
        MainMenu.setCurrentFood(new Food(name, price, currentRestaurant.getRestaurantID(), foodType));
        return Message.SUCCESS;
    }

    public void handleEditFoodType(String choice) {
    }
}
