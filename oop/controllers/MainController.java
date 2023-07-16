package controllers;

import enums.Message;
import models.*;
import views.MainMenu;
import views.Menu;

import java.util.ArrayList;

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
        return MainMenu.getCurrentRestaurant().getFood();
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
        for(int i=0 ; i<MainMenu.getCurrentRestaurant().getFood().size() ; i++)
            if(MainMenu.getCurrentRestaurant().getFood().get(i).getName().contains(choice))
                searchedFoods.add(MainMenu.getCurrentRestaurant().getFood().get(i));
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
        if (i<MainMenu.getCurrentRestaurant().getFood().size())
            return MainMenu.getCurrentRestaurant().getFood().get(i);
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
        Food newFood = new Food(name, price, currentRestaurant.getID(), foodType);
        MainMenu.setCurrentFood(newFood);
        currentRestaurant.setFood(newFood);
        return Message.SUCCESS;
    }

}
