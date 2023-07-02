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
    public ArrayList<Saleable> handleShowProductAndServices() {
        return Saleable.getAllItems();
    }
    public ArrayList<Restaurant> handleShowRestaurants() {
        return Restaurant.getAllRestaurant();
    }
    public Restaurant handleChooseRestaurant(String choice) {
        int i = Integer.parseInt(choice.trim())-1;
        if (i<Restaurant.getAllRestaurant().size())
            return Restaurant.getAllRestaurant().get(i);
        else
            return null;
    }

    public Message handleAddProduct(String name, int price, int capacity, String description) {
        for (Saleable item : Saleable.getAllItems()) {
            if (item instanceof Product && item.getName().equals(name) && item.getPrice() == price
                    && ((Product) item).getCapacity() == capacity
                    && ((Product) item).getDescription().equals(description)) {
                return Message.PRODUCT_EXIST;
            }
        }

        new Product(name, price, description, capacity);

        return Message.SUCCESS;

    }
    public Message handleAddRestaurant(String name, int locationNode) {
        User loggedInUser = Menu.getLoggedInUser();
        MainMenu.setCurrentRestaurant(new Restaurant(name, loggedInUser.getUserId(),locationNode));
        return Message.SUCCESS;
    }
    public Message handleAddFood(String name, int price, int foodType) {
        User loggedInUser = Menu.getLoggedInUser();
        Restaurant currentRestaurant = MainMenu.getCurrentRestaurant();
        MainMenu.setCurrentFood(new Food(name, price, currentRestaurant.getID(), foodType));
        return Message.SUCCESS;
    }

    public void handleShowLocation() {
        System.out.println("location of restaurant : "+MainMenu.getCurrentRestaurant().getLocationNode());
    }

    public void editLocation(String location) {
        MainMenu.getCurrentRestaurant().setLocationNode(Integer.parseInt(location));
    }

    public void showFoodTypes() {
        for (int i=0; i<MainMenu.getCurrentRestaurant().getFoodType().size(); i++)
            System.out.println((i+1)+". "+ FoodType.getFoodTypeFromInt(MainMenu.getCurrentRestaurant().getFoodType().get(i)));
    }

    public void editFoodType() {

    }
}
