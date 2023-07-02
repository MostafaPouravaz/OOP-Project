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
        System.out.println("3. back");

    }

    private void showCustomerOptions() {
        System.out.println("1. Show restaurants");
        System.out.println("2. Search restaurants");
        System.out.println("3. back");
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
                this.showRestaurantsForCustomer();
                break;
            case "2":
                this.searchRestaurant();
                break;
            case "3":
                RegisterMenu.getInstance().run();
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
        setCurrentRestaurant(controller.handleChooseRestaurant(choice));
        this.showRestaurantOptions();
    }
    private void showRestaurantOptions() {
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
            case "4" -> this.editFoodType();
            case "5" -> this.menu();
            case "6" -> this.run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void menu() {
        //name id price discount
        System.out.println("Menu : ");
        for (int i=0; i<getCurrentRestaurant().getFood().size(); i++){
            System.out.print((i+1)+ ". \nName: " + getCurrentRestaurant().getFood().get(i).getName()+
                    " | ID: "+getCurrentRestaurant().getFood().get(i).getID()+" | Price: "+ getCurrentRestaurant().getFood().get(i).getPrice());
            if (getCurrentRestaurant().getFood().get(i).isActive())
                System.out.print(" | Active : YES");
            else System.out.print(" | Active: NO");
            if (getCurrentRestaurant().getFood().get(i).discountActive())
                System.out.print(" | discountActive : YES" + " | discount percent :" + getCurrentRestaurant().getFood().get(i).getDiscount()+"%");
            else System.out.print(" | discountActive: NO");
        }
        System.out.println((getCurrentRestaurant().getFood().size()+1)+". add food\n" +(getCurrentRestaurant().getFood().size()+2) +". back\nchoose one");
        int j= getScanner().nextInt()-1;
        if (j>getCurrentRestaurant().getFood().size()) {
            System.out.println(Message.INVALID_CHOICE);
            menu();
        }else if (j==getCurrentRestaurant().getFood().size())
            addFood();
        else if (j==getCurrentRestaurant().getFood().size()+1)
            showRestaurantOptions();
        else {
            setCurrentFood(getCurrentRestaurant().getFood().get(j));
            foodMenu();
        }
    }
    private void foodMenu(){
        //edit food name and price add food delete food active and deActive food discount food
        System.out.println(getCurrentFood().getName() + " options : \n1. edit food name\n2. edit price\n3. delete food\n" +
                "4. food Activation\n5. discount Activation\n6.back");//kamel nashode
        String choice = this.getChoice();
        switch (choice.trim()) {
            case "1" -> this.editFoodName();
            case "2" -> this.editPrice();
            case "3" -> this.deleteFood();
            case "4" -> this.foodActivation();
            case "5" -> this.discountActivation();
            case "6" -> this.menu();
            default -> System.out.println(Message.INVALID_CHOICE);
        }

    }

    private void discountActivation() {
        if (getCurrentFood().discountActive())
            System.out.println("You have an active discount!");
        else {
            System.out.println("enter discount percent: ");
            int discount = getScanner().nextInt();
            System.out.println("enter timestamp into minutes: ");
            int timestamp = getScanner().nextInt();
            if (discount>50)
                System.out.println(Message.WRONG_CREDENTIALS);
            else {
                currentFood.setDiscount(discount);
                currentFood.discounter(timestamp);
                System.out.println(Message.SUCCESS);
            }
        }
        foodMenu();
    }

    private void foodActivation() {
        if (getCurrentFood().isActive()){
            System.out.println("Are you sure you want to disActive food?");
            String choice = getScanner().nextLine().trim();
            if (choice.equals("yes"))
                getCurrentFood().setActive(false);
            else System.out.println("process canceled");
        }else {
            System.out.println("Are you sure you want to Active food?");
            String choice = getScanner().nextLine().trim();
            if (choice.equals("yes"))
                getCurrentFood().setActive(true);
            else System.out.println("process canceled");
        }
        foodMenu();
    }

    private void deleteFood() {
        getCurrentRestaurant().getFood().remove(getCurrentFood());
        setCurrentFood(null);
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editPrice() {
        System.out.println("enter new price : ");
        getCurrentFood().setPrice(getScanner().nextInt());
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editFoodName() {
        System.out.println("enter new name : ");
        getCurrentFood().setName(getScanner().nextLine().trim());
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editFoodType(){
        this.controller.showFoodTypes();
        String choice = getInput("choose one to edit");
        setCurrentFoodType(FoodType.getFoodTypeFromInt(Integer.parseInt(this.getInput("""
                choose new Food type between these items:\s
                1. FastFood
                2. IranianFood
                3. SeaFood
                4. Appetizer
                5. other"""))-1));
        System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE?");
        if (getScanner().next().trim().equals("yes"))
            getCurrentRestaurant().editFoodType(Integer.parseInt(choice.trim())-1,getCurrentFoodType());
        else System.out.println("edit food type cancelled");

    }

    private void showRestaurantsForCustomer() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        System.out.println("Restaurants list :");
        for (int i=0; i<allRestaurants.size(); i++)
            System.out.println(allRestaurants.get(i).getID()+". "+allRestaurants.get(i).getName());

        this.chooseRestaurantForCostumer();
    }
    private void chooseRestaurantForCostumer() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        String choice = this.getChoice();
        setCurrentRestaurant(controller.handleChooseRestaurant(choice));
        this.showRestaurantOptionsForCostumer();
    }
    private void showRestaurantOptionsForCostumer() {
        ArrayList<Food> allFoods = this.controller.handleShowFoods();
        System.out.println("Foods list :");
        for (int i=0; i<allFoods.size(); i++)
            System.out.println(allFoods.get(i).getID()
                    +". "+allFoods.get(i).getName()+"       "+allFoods.get(i).getPrice()+"$");

        this.chooseFoodForCostumer();
    }
    private void chooseFoodForCostumer(){
        //continue
    }

    private void searchRestaurant() {
        String choice = this.getChoice();

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
