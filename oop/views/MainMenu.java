package views;

import controllers.MainController;
import enums.FoodType;
import enums.Message;
import models.*;

import java.util.ArrayList;

public class MainMenu extends Menu{
    private static MainMenu instance = null;

    private final MainController controller;
    private static Restaurant currentRestaurant = null;
    public static Restaurant getCurrentRestaurant() {
        return MainMenu.currentRestaurant;
    }

    public static void setCurrentRestaurant(Restaurant restaurant) {
        MainMenu.currentRestaurant = restaurant;
    }
    private static FoodType currentFoodType = null;
    public static FoodType getCurrentFoodType() {
        return MainMenu.currentFoodType;
    }

    public static void setCurrentFoodType(FoodType foodType) {
        MainMenu.currentFoodType = foodType;
    }
    private static Food currentFood = null;
    public static Food getCurrentFood() {
        return MainMenu.currentFood;
    }

    public static void setCurrentFood(Food food) {
        MainMenu.currentFood = food;
    }
    private MainMenu() {
        this.controller = MainController.getInstance();

    }

    private static void setInstance(MainMenu instance) {
        MainMenu.instance = instance;
    }

    public static MainMenu getInstance() {
        if (MainMenu.instance == null) {
            MainMenu.setInstance(new MainMenu());
        }
        return MainMenu.instance;
    }

    private void showVendorOptions() {
        System.out.println("1. add new Restaurant");
        System.out.println("2. show Restaurants");
        System.out.println("2. back");

    }

    private void showCustomerOptions() {
        System.out.println("2. buy a product");
        System.out.println("3. add new service");
        System.out.println("4. add comment");
        System.out.println("5. profile");

    }

    @Override
    public void run() {
        this.showOptions();

        String choice = this.getChoice();

        User loggedInUser = Menu.getLoggedInUser();

        if (loggedInUser instanceof Vendor) {
            this.handleVendorChoice(choice);
        } else if (loggedInUser instanceof Customer) {
            this.handleCustomerChoice(choice);
        }

    }
    public void foodAndFoodTypeOptions() {
        System.out.println("1. add new FoodType");
        System.out.println("2. add new Food");
        System.out.println("3. back");
        String choice = this.getChoice();
        handleFoodAndFoodTypeChoice(choice);
        foodAndFoodTypeOptions();
    }
    public void handleFoodAndFoodTypeChoice(String choice) {
        switch (choice) {
            case "1" -> this.addFoodType();
            case "2" -> this.addFood();
            case "3" -> this.addRestaurant();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void handleCustomerChoice(String choice) {
        switch (choice) {
            case "1":
                this.showRestaurants();
                break;
            case "2":
                this.buyProduct();
                break;
            case "3":
                this.addService();
                break;

            case "4":
                this.addComment();
                break;

            case "5":
                this.goToProfile();
                break;

            default:
                System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void handleVendorChoice(String choice) {
        switch (choice) {
            case "1" -> this.addRestaurant();
            case "2" -> this.showRestaurants();
            case "3" -> RegisterMenu.getInstance().run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void goToProfile() {
    }

    private void editProduct() {
    }

    private void addProduct() {
        System.out.println("enter information");
        String name = this.getInput("enter name");
        int price = Integer.parseInt(this.getInput("enter price"));
        int capacity = Integer.parseInt(this.getInput("enter capacity"));
        String description = this.getInput("enter description");

        Message message = this.controller.handleAddProduct(name, price, capacity, description);
        System.out.println(message == Message.SUCCESS ? "product added successfully" : message);
        this.run();
    }
    private void addRestaurant() {
        System.out.println("enter information");
        String name = this.getInput("enter name of Restaurant ");
        int locationNode = Integer.parseInt(this.getInput("enter locationNode "));

        Message message = this.controller.handleAddRestaurant(name, locationNode);
        System.out.println(message == Message.SUCCESS ? "Restaurant added successfully" : message);
        this.addFoodType();
    }
    private void addFoodType() {
        System.out.println("enter foodType");
        setCurrentFoodType(FoodType.getFoodTypeFromInt(Integer.parseInt(this.getInput("""
                choose between these items:\s
                1. FastFood
                2. IranianFood
                3. SeaFood
                4. Appetizer
                5. other"""))-1));
        getCurrentRestaurant().setFoodType(getCurrentFoodType().ordinal());
        System.out.println("FoodType added successfully");
        this.addFood();
    }
    private void addFood() {
        System.out.println("enter Food");
        String name = this.getInput("enter name of Food ");
        int price = Integer.parseInt(this.getInput("enter price "));

        Message message = this.controller.handleAddFood(name, price, FoodType.getIntFromFoodType(getCurrentFoodType()));
        System.out.println(message == Message.SUCCESS ? "Food added successfully" : message);
        this.run();
    }

    private void showRestaurants() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        System.out.println("Restaurants list :");
        for (int i=0; i<allRestaurants.size(); i++)
            System.out.println((i+1)+". "+allRestaurants.get(i).getName());

        this.chooseRestaurant();
    }
    private void chooseRestaurant() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        String choice = this.getChoice();
        Restaurant restaurant = controller.handleChooseRestaurant(choice);
        this.showRestaurantOptions(restaurant);
    }
    private void showRestaurantOptions(Restaurant restaurant) {
    //show and edit location food type select menu edit food
        System.out.println("""
                1. show location\s
                2. edit location\s
                3. show FoodTypes\s
                4. edit FoodType\s
                5. menu\s
                6. back
                """);
        String choice = this.getChoice();
        switch (choice.trim()) {
            case "1" -> this.controller.handleShowLocation();
            case "2" -> this.controller.editLocation(getInput("enter new location"));
            case "3" -> this.controller.showFoodTypes();
            case "4" -> this.controller.editFoodType();
            case "5" -> this.menu();
            case "6" -> this.run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void menu() {

    }


    private void addComment() {
    }

    private void addService() {
    }

    private void buyProduct() {
    }

    @Override
    protected void showOptions() {
        System.out.println("enter one of the choices");

        User loggedInUser = Menu.getLoggedInUser();

        if (loggedInUser instanceof Vendor) {
            this.showVendorOptions();
        } else if (loggedInUser instanceof Customer) {
            this.showCustomerOptions();
        }
    }

}
