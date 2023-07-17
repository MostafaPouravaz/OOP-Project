package views;

import controllers.MainController;
import enums.FoodType;
import enums.Message;
import models.*;
import models.Order;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class MainMenu extends Menu{
    private static MainMenu instance = null;
    LocalDateTime startTime;
    private static int period ;
    private static boolean isDelivery = false;
    private final MainController controller;
    private static Restaurant currentRestaurant = null;
    private static Cart currentCart = null;
    private static Order currentOrder = null;
    private static Delivery currentDelivery = null;

    public static Restaurant getCurrentRestaurant() {
        return MainMenu.currentRestaurant;
    }

    public static void setCurrentRestaurant(Restaurant restaurant) {
        MainMenu.currentRestaurant = restaurant;
    }
    public static void setCurrentCart(int customerID , int restaurantID) {
        MainMenu.currentCart = new Cart(customerID , restaurantID);
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
        System.out.println("3. access order history");
        System.out.println("4. display cart status");
        System.out.println("5. confirm order");
        System.out.println("6. charge account");
        System.out.println("7. display account charge");
        System.out.println("8. show estimated delivery time");
        System.out.println("9. back");
    }

    private void handleCustomerChoice(String choice) throws IOException {
        switch (choice) {
            case "1" -> this.showRestaurantsForCustomer();
          //  case "2" -> this.searchRestaurant();
         //   case "3" -> this.handleAccessOrderHistoryForCustomer();
            //   case "4" -> this.handleDisplayCartStatusForCustomer();
         //   case "5" -> this.handleConfirmOrderForCustomer();
         //   case "6" -> this.handleChargeAccountForCustomer();
         //   case "7" -> this.handleDisplayAccountChargeForCustomer();
         //   case "8" -> this.handleShowEstimatedDeliveryTime();
            case "9" -> RegisterMenu.getInstance().run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    @Override
    public void run() {
        this.showOptions();

        String choice = this.getChoice();

        User loggedInUser = Menu.getLoggedInUser();

        if (loggedInUser instanceof Vendor) {
            this.handleVendorChoice(choice);
        } else if (loggedInUser instanceof Customer) {
            try {
                this.handleCustomerChoice(choice);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void foodAndFoodTypeOptions() {
        System.out.println("1. add new FoodType");
        System.out.println("2. add new Food");
        System.out.println("3. back");
        String choice = this.getChoice();
        handleFoodAndFoodTypeChoice(choice);
    }
    public void handleFoodAndFoodTypeChoice(String choice) {
        switch (choice) {
            case "1" -> this.addFoodType();
            case "2" -> this.addFood();
            case "3" -> this.addRestaurant();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
        foodAndFoodTypeOptions();
    }

    private void handleVendorChoice(String choice) {
        switch (choice) {
            case "1" -> this.addRestaurant();
            case "2" -> this.showRestaurants();
            case "3" -> RegisterMenu.getInstance().run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }
    private void addRestaurant() {
        System.out.println("enter information");
        String name = this.getInput("enter name of Restaurant ");
        int locationNode = Integer.parseInt(this.getInput("enter locationNode "));

        Message message = this.controller.handleAddRestaurant(name, locationNode);
        System.out.println(message == Message.SUCCESS ? "Restaurant added successfully" : message);
        System.out.println("do you want to add foodType?");
        String ans = this.getChoice();
        if (ans.equals("yes"))
            this.addFoodType();
        else if (ans.equals("no"))
            this.run();
        else {
            System.out.println(Message.INVALID_CHOICE);
            this.run();
        }
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
        System.out.println("do you want to add food?");
        String ans = this.getChoice();
        if (ans.equals("yes"))
            this.addFood();
        else if (ans.equals("no"))
            this.run();
        else {
            System.out.println(Message.INVALID_CHOICE);
            this.run();
        }
    }
    private void addFood() {
        System.out.println("enter Food");
        String name = this.getInput("enter name of Food ");
        int price = Integer.parseInt(this.getInput("enter price "));

        Message message = this.controller.handleAddFood(name, price, FoodType.getIntFromFoodType(getCurrentFoodType()));
        System.out.println(message == Message.SUCCESS ? "Food added successfully" : message);
        System.out.println("do you want to add another food?");
        String ans = this.getChoice();
        if (ans.equals("yes"))
            this.addFood();
        else if (ans.equals("no"))
            this.run();
        else {
            System.out.println(Message.INVALID_CHOICE);
            this.run();
        }
    }

    private void showRestaurants() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        System.out.println("Restaurants list :");
        for (int i=0; i<allRestaurants.size(); i++)
            System.out.println((i+1)+". "+allRestaurants.get(i).getName());
        this.chooseRestaurant();
    }
    private void chooseRestaurant() {
        System.out.println("choose one or enter 0 to back");
        String choice = this.getChoice();
        if (choice.equals("0"))
            this.run();
        else if (controller.handleChooseRestaurant(choice)==null){
            System.out.println(Message.INVALID_CHOICE);
            this.showRestaurants();
        }else {
            setCurrentRestaurant(controller.handleChooseRestaurant(choice));
            this.showRestaurantOptions();
        }
    }
    private void showRestaurantOptions() {
    //show and edit location food type select menu edit food
        System.out.println("""
                1. show location\s
                2. edit location\s
                3. show and add FoodTypes\s
                4. edit FoodType\s
                5. display ratings\s
                6. display comments\s
                7. show order history\s
                8. open orders\s
                9. menu\s
                10. back
                """);
        String choice = this.getChoice();
        switch (choice) {
            case "1" -> this.showLocation();
            //case "2" -> this.editLocation();
            case "3" -> this.showAndAddFoodTypes();
            case "4" -> this.editFoodType();
            case "5" -> this.displayRestaurantRatings();
            case "6" -> this.displayRestaurantComments();
            case "7" -> this.showOrderHistory();
            case "8" -> this.openOrders();
            case "9" -> this.menu();
            case "10" -> this.run();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }

    private void openOrders() {
    //edit status and deliver time
        System.out.println("Open Orders :");
        for (int i=0; i<currentOrder.openOrders().size(); i++){
            System.out.println((i+1) + ". customer Name : " + Objects.requireNonNull(User.getUserByUserID(currentOrder.openOrders().get(i).getCustomerID())).getUsername() );
        }
        System.out.println("if you want more details about each order, choose it please." +
                "\nelse enter no.");
        String choice = this.getChoice();
        if (choice.equals("no"))
            showRestaurantOptions();
        else{
            int num = Integer.parseInt(choice)-1;
            System.out.println("Customer Name : " + Objects.requireNonNull(User.getUserByUserID(currentOrder.openOrders().get(num).getCustomerID())).getUsername()
                    +" | Destination node : " + currentOrder.openOrders().get(num).getDestinationNode() + " | Final price : " + currentOrder.openOrders().get(num).getFinalPrice()
                    + "\nStatus : " + currentOrder.openOrders().get(num).getStatus() +" | Order confirmation time : " + currentOrder.openOrders().get(num).getStartTime() + " | Estimated order delivery time : " + currentOrder.openOrders().get(num).getStartTime().plusSeconds(currentOrder.openOrders().get(num).getEstimatedTime())
                    +"\nOrdered Foods :");
            for (int i=0; i<currentOrder.openOrders().get(num).getOrderedFoods().size(); i++){
                System.out.println((i+1) + ". " + currentOrder.openOrders().get(num).getOrderedFoods().get(i).getName());
            }
            System.out.println("Choose option : \n1. Change status\n2.Change estimated order delivery time\n3.back");
            String choice1 = this.getChoice();
            switch (choice1) {
                case "1" -> this.changeStatus(num);
                case "2" -> this.changeEstimatedDeliveryTime(num);
                case "3" -> this.showRestaurantOptions();
                default -> System.out.println(Message.INVALID_CHOICE);
            }
        }
    }

    private void changeEstimatedDeliveryTime(int num) {
        int estimatedTime = Integer.parseInt(this.getInput("Enter new estimated time in seconds"));
        currentOrder.openOrders().get(num).setEstimatedTime(estimatedTime);
        System.out.println(Message.SUCCESS);
        openOrders();
    }

    private void changeStatus(int num) {
        String newStatus = this.getInput("Enter new status");
        currentOrder.openOrders().get(num).setStatus(newStatus);
        System.out.println(Message.SUCCESS);
        openOrders();
    }

    private void showOrderHistory() {
        System.out.println("Order History :");
        for (int i=0; i<currentOrder.pastOrders().size(); i++){
            System.out.println((i+1) + ". customer Name : " + Objects.requireNonNull(User.getUserByUserID(currentOrder.pastOrders().get(i).getCustomerID())).getUsername() );
        }
        System.out.println("if you want more details about each order, choose it please." +
                "\nelse enter no.");
        String choice = this.getChoice();
        if (choice.equals("no"))
            showRestaurantOptions();
        else{
            int num = Integer.parseInt(choice)-1;
            System.out.println("customer Name : " + Objects.requireNonNull(User.getUserByUserID(currentOrder.pastOrders().get(num).getCustomerID())).getUsername()
            +" | destination node : " + currentOrder.pastOrders().get(num).getDestinationNode() + " | final price : " + currentOrder.pastOrders().get(num).getFinalPrice()
            +"\nOrder confirmation time : " + currentOrder.pastOrders().get(num).getStartTime() + " | Order delivery time : " + currentOrder.pastOrders().get(num).getStartTime().plusSeconds(currentOrder.pastOrders().get(num).getEstimatedTime())
            +"\nOrdered Foods :");
            for (int i=0; i<currentOrder.pastOrders().get(num).getOrderedFoods().size(); i++){
                System.out.println((i+1) + ". " + currentOrder.pastOrders().get(num).getOrderedFoods().get(i).getName());
            }
            System.out.println("press a key to back");
            if (!this.getChoice().isEmpty())
                showRestaurantOptions();
        }
    }

    private void displayRestaurantComments() {
        System.out.println("choose one:\n0.back");
        getCurrentRestaurant().showComments();
        int choice = Integer.parseInt(this.getChoice());
        if (choice == 0)
            this.showRestaurantOptions();
        else if (choice>getCurrentRestaurant().getAllComments().size()){
            System.out.println(Message.INVALID_CHOICE);
            this.showRestaurantOptions();
        }else restaurantCommentOptions(choice-1);
    }

    private void displayRestaurantRatings() {
        System.out.println("rate of restaurant : "+getCurrentRestaurant().getFinalRate());
        System.out.println("press a button to back");
        String choice = this.getChoice();
        if (choice!=null)
            this.showRestaurantOptions();
    }
    private void restaurantCommentOptions(int choice) {
        System.out.println(getCurrentRestaurant().getAllComments().get(choice).getComment());
        if (getCurrentRestaurant().getAllComments().get(choice).isResponseExists()){
            System.out.println("you have added response to this comment before\nyour response : "+getCurrentRestaurant().getAllComments().get(choice).getResponse()+"\ndo you want to edit it ?");
            String ans = this.getChoice();
            if (ans.equals("no"))
                this.displayRestaurantComments();
            else if (ans.equals("yes")){
                getCurrentRestaurant().getAllComments().get(choice).setResponse(choice,this.getInput("enter your new response"));
                System.out.println(Message.SUCCESS);
                this.displayRestaurantComments();
            }else {
                System.out.println(Message.INVALID_CHOICE);
                this.displayRestaurantComments();
            }
        }else {
            System.out.println("you have not add any response on this comment\ndo you want to add response?");
            String ans = this.getChoice();
            if (ans.equals("no"))
                this.displayRestaurantComments();
            else if (ans.equals("yes")){
                getCurrentRestaurant().getAllComments().get(choice).setResponse(choice,this.getInput("enter your response"));
                System.out.println(Message.SUCCESS);
                this.displayRestaurantComments();
            }else {
                System.out.println(Message.INVALID_CHOICE);
                this.displayRestaurantComments();
            }
        }
    }
    private void showAndAddFoodTypes() {
        System.out.println("Food types are :");
        for (int i=0; i<getCurrentRestaurant().getFoodTypes().size(); i++)
            System.out.println((i+1)+". "+ FoodType.getFoodTypeFromInt(getCurrentRestaurant().getFoodTypes().get(i)));
        System.out.println("do you want to add foodType?");
        String choice = this.getChoice();
        if (choice.equals("yes"))
            this.addFoodType();
        else if (choice.equals("no"))
            this.showRestaurantOptions();
        else {
            System.out.println(Message.INVALID_CHOICE);
            this.showRestaurantOptions();
        }
    }
    public static int showLocation(){
       return getCurrentRestaurant().getLocationNode();
    }
    public static void editLocation(int node){
        getCurrentRestaurant().setLocationNode(node);
    }
    private void menu() {
        //name id price discount
        System.out.println("Menu : ");
        for (int i=0; i<getCurrentRestaurant().getFoods().size(); i++){
            System.out.print((i+1)+ ". \nName: " + getCurrentRestaurant().getFoods().get(i).getName()+
                    " | ID: "+getCurrentRestaurant().getFoods().get(i).getID()+" | Price: "+ getCurrentRestaurant().getFoods().get(i).getPrice());
            if (getCurrentRestaurant().getFoods().get(i).isActive())
                System.out.print(" | Active : YES");
            else System.out.print(" | Active: NO");
            if (getCurrentRestaurant().getFoods().get(i).discountActive())
                System.out.print(" | discountActive : YES" + " | discount percent :" + getCurrentRestaurant().getFoods().get(i).getDiscount()+"%");
            else System.out.print(" | discountActive: NO");
        }
        System.out.println((getCurrentRestaurant().getFoods().size()+1)+". add food\n" +(getCurrentRestaurant().getFoods().size()+2) +". back\nchoose one");
        int j= Integer.parseInt(this.getChoice())-1;
        if (j>getCurrentRestaurant().getFoods().size()) {
            System.out.println(Message.INVALID_CHOICE);
            menu();
        }else if (j==getCurrentRestaurant().getFoods().size())
            addFood();
        else if (j==getCurrentRestaurant().getFoods().size()+1)
            showRestaurantOptions();
        else {
            setCurrentFood(getCurrentRestaurant().getFoods().get(j));
            foodMenu();
        }
    }
    private void foodMenu(){
        //edit food name and price add food delete food active and deActive food discount food
        System.out.println(getCurrentFood().getName() + " options : \n1. edit food name\n2. edit price\n3. delete food\n" +
                "4. food Activation\n5. discount Activation\n6. display Comments\n7. display Ratings\n8.back");
        String choice = this.getChoice();
        switch (choice.trim()) {
            case "1" -> this.editFoodName();
            case "2" -> this.editPrice();
            case "3" -> this.deleteFood();
            case "4" -> this.foodActivation();
            case "5" -> this.discountActivation();
            case "6" -> this.displayFoodComments();
            case "7" -> this.displayFoodRatings();
            case "8" -> this.menu();
            default -> System.out.println(Message.INVALID_CHOICE);
        }

    }

    private void displayFoodRatings() {
        System.out.println("rate of food : "+getCurrentFood().getFinalRate());
        System.out.println("press a button to back");
        String choice = this.getChoice();
        if (choice!=null)
            this.foodMenu();
    }

    private void displayFoodComments() {
        System.out.println("choose one:\n0.back");
        getCurrentFood().showComments();
        int choice = Integer.parseInt(this.getChoice());
        if (choice == 0)
            this.foodMenu();
        else if (choice>getCurrentFood().getComments().size()){
            System.out.println(Message.INVALID_CHOICE);
            this.foodMenu();
        }else foodCommentOptions(choice-1);

    }

    private void foodCommentOptions(int choice) {
        System.out.println(getCurrentFood().getComments().get(choice).getComment());
        if (getCurrentFood().getComments().get(choice).isResponseExists()){
            System.out.println("you have added response to this comment before\nyour response : "+getCurrentFood().getComments().get(choice).getResponse()+"\ndo you want to edit it ?");
            String ans = this.getChoice();
            if (ans.equals("no"))
                this.displayFoodComments();
            else if (ans.equals("yes")){
                getCurrentFood().getComments().get(choice).setResponse(choice,this.getInput("enter your new response"));
                System.out.println(Message.SUCCESS);
                this.displayFoodComments();
            }else {
                System.out.println(Message.INVALID_CHOICE);
                this.displayFoodComments();
            }
        }else {
            System.out.println("you have not add any response on this comment\ndo you want to add response?");
            String ans = this.getChoice();
            if (ans.equals("no"))
                this.displayFoodComments();
            else if (ans.equals("yes")){
                getCurrentFood().getComments().get(choice).setResponse(choice,this.getInput("enter your response"));
                System.out.println(Message.SUCCESS);
                this.displayFoodComments();
            }else {
                System.out.println(Message.INVALID_CHOICE);
                this.displayFoodComments();
            }
        }
    }

    private void discountActivation() {
        if (getCurrentFood().discountActive())
            System.out.println("You have an active discount!");
        else {
            System.out.println("enter discount percent: ");
            int discount = Integer.parseInt(this.getChoice());
            System.out.println("enter timestamp into minutes: ");
            int timestamp = Integer.parseInt(this.getChoice());
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
            String choice = this.getChoice();
            if (choice.equals("yes"))
                getCurrentFood().setActive(false);
            else System.out.println("process canceled");
        }else {
            System.out.println("Are you sure you want to Active food?");
            String choice = this.getChoice();
            if (choice.equals("yes"))
                getCurrentFood().setActive(true);
            else System.out.println("process canceled");
        }
        foodMenu();
    }

    private void deleteFood() {
        getCurrentRestaurant().getFoods().remove(getCurrentFood());
        setCurrentFood(null);
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editPrice() {
        System.out.println("enter new price : ");
        getCurrentFood().setPrice(Integer.parseInt(this.getChoice()));
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editFoodName() {
        System.out.println("enter new name : ");
        getCurrentFood().setName(this.getChoice());
        System.out.println(Message.SUCCESS);
        foodMenu();
    }

    private void editFoodType(){
        System.out.println("Food types are :");
        for (int i=0; i<getCurrentRestaurant().getFoodTypes().size(); i++)
            System.out.println((i+1)+". "+ FoodType.getFoodTypeFromInt(getCurrentRestaurant().getFoodTypes().get(i)));
        String choice = getInput("choose one to edit");
        System.out.println("""
                choose new FoodType between these items:\s
                1. FastFood
                2. IranianFood
                3. SeaFood
                4. Appetizer
                5. other""");
        setCurrentFoodType(FoodType.getFoodTypeFromInt(Integer.parseInt(this.getChoice())-1));
        System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE?");
        if (this.getChoice().equals("yes"))
            getCurrentRestaurant().editFoodType(Integer.parseInt(choice.trim())-1,getCurrentFoodType());
        else System.out.println("edit food type cancelled");
        this.showRestaurantOptions();
    }

    private void showRestaurantsForCustomer() {
        ArrayList<Restaurant> allRestaurants = this.controller.handleShowRestaurants();
        System.out.println("0. back");
        System.out.println("Restaurants list :");
        for (Restaurant allRestaurant : allRestaurants)
            System.out.println(allRestaurant.getRestaurantID() + ". " + allRestaurant.getName());

        this.chooseRestaurantForCustomer();
    }
    private void chooseRestaurantForCustomer() {
        String choice = this.getChoice();
        if(choice.trim().equals("0"))
            this.run();
        else {
            setCurrentRestaurant(controller.handleChooseRestaurant(choice));
            User loggedInUser = Menu.getLoggedInUser();

            setCurrentCart(loggedInUser.getUserId() , currentRestaurant.getRestaurantID());
            this.handleShowRestaurantOptionForCustomer();
        }
    }

    private void handleShowRestaurantOptionForCustomer(){
        this.showRestaurantOptionsForCustomer();

        String choice = this.getChoice();

        this.handleCustomerChoiceRestaurantOption(choice);

    }
    private void showRestaurantOptionsForCustomer(){
        System.out.println("enter one of the choices");

        System.out.println("1. Show all foods");
        System.out.println("2. Search foods");
        System.out.println("3. display restaurant comments");
        System.out.println("4. add new comment for restaurant");
        System.out.println("5. edit restaurant comment");
        System.out.println("6. display restaurant rating");
        System.out.println("7. submit restaurant rating");
        System.out.println("8. edit restaurant rating");
        System.out.println("9. back");
    }
    private void handleCustomerChoiceRestaurantOption(String choice) {
        switch (choice.trim()) {
            case "1" -> this.showAllFoodForCustomer();
           // case "2" -> this.searchFoodForCustomer();
           // case "3" -> this.handleDisplayRestaurantComment();
           // case "4" -> this.handleAddNewCommentForRestaurant();
           // case "5" -> this.handleEditRestaurantCommentForCustomer();
           // case "6" -> this.handleDisplayRatingForCustomer();
           // case "7" -> this.handleSubmitRatingForCustomer();
           // case "8" -> this.handleEditRestaurantRatingForCustomer();
            case "9" -> this.showRestaurantsForCustomer();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }
    private void showAllFoodForCustomer(){
        ArrayList<Food> allFoods = this.controller.handleShowFoods();
        System.out.println("Foods list :");
        for (Food allFood : allFoods)
            System.out.println(allFood.getID()
                    + ". " + allFood.getName() + "       " + allFood.getPrice() + "$");
        this.chooseFoodForCustomer();
    }

    private void chooseFoodForCustomer(){
        String choice = this.getChoice();

        setCurrentFood(controller.handleChooseFood(choice));
        this.handleShowFoodOptionForCustomer();
    }

    private void handleShowFoodOptionForCustomer(){
        this.showFoodOptionsForCustomer();

        String choice = this.getChoice();

        this.handleCustomerChoiceFoodOption(choice);

    }
    private void showFoodOptionsForCustomer(){
        System.out.println("enter one of the choices");

        System.out.println("1. display comments of food");
        System.out.println("2. add new comment for food");
        System.out.println("3. edit your comment");
        System.out.println("4. display rating of food");
        System.out.println("5. submit rating");
        System.out.println("6. edit your rating");
        System.out.println("7. add this food to cart");
        System.out.println("8. back");
    }

    private void handleCustomerChoiceFoodOption(String choice) {
        switch (choice.trim()) {
           // case "1" -> this.handleDisplayCommentsOfFoodForCustomer();
           // case "2" -> this.handleAddNewCommentForFoodForCustomer();
            //case "3" -> this.handleEditCommentsOfFoodForCustomer();
           // case "4" -> this.handleDisplayRatingOfFoodForCustomer();
           // case "5" -> this.handleSubmitRatingForFoodForCustomer();
            //case "6" -> this.handleEditRatingForFoodForCustomer();
            case "7" -> this.handleAddFoodToCart();
            case "8" -> this.showAllFoodForCustomer();
            default -> System.out.println(Message.INVALID_CHOICE);
        }
    }
    public static void handleDisplayCommentsOfFoodForCustomer(ArrayList<String> showCommentWithResponse){
        for (int i = 0; currentFood.getComments().size()>i; i++){
            showCommentWithResponse.add(3*i , currentFood.getComments().get(i).getCommentID() + " :");
            showCommentWithResponse.add(3*i+1 , Customer.getUserByUserID(currentFood.getComments()
                    .get(i).getCustomerID()).getUsername()
                    + " :" +  currentFood.getComments().get(i).getComment());
            if (currentFood.getComments().get(i).isResponseExists())
                showCommentWithResponse.add(3*i+2 ,"manager's response : " + currentFood.getComments().get(i).getResponse() );
            else
                showCommentWithResponse.add(3*i+2 ,"manager's response : "  );
        }
    }
    public static void handleAddNewCommentForFoodForCustomer(String comment){
        User loggedInUser = Menu.getLoggedInUser();

        currentFood.addComment(loggedInUser.getUserId() , comment);
    }
    public static boolean handleEditCommentsOfFoodForCustomer(String comment , int ID){
        User loggedInUser = Menu.getLoggedInUser();
        if(currentFood.getComments().get(ID).getCustomerID() == loggedInUser.getUserId()) {
            currentFood.editComment(loggedInUser.getUserId(), comment);
            return true;
        }
        return false;
    }
    public static void handleDisplayRatingOfFoodForCustomer(double rate){
       rate =  currentFood.getFinalRate();
    }
    public static void handleSubmitRatingForFoodForCustomer(double rate){
        User loggedInUser = Menu.getLoggedInUser();

        currentFood.addRate(loggedInUser.getUserId() ,rate);
    }
    public static void handleEditRatingForFoodForCustomer(double rate){

        User loggedInUser = Menu.getLoggedInUser();

        currentFood.editRate(loggedInUser.getUserId() ,rate);

    }
    public static void handleAddFoodToCart(){
        currentCart.addFood(currentFood);
    }
    public ArrayList<String> searchFoodForCustomer(String searched){
        ArrayList<String> foods = new ArrayList<>();

        String choice = searched ;
        ArrayList<Food> allSearchedFoods = this.controller.handleSearchFoods(choice);
        for (Food allSearchedFood : allSearchedFoods)
            foods.add(String.valueOf(allSearchedFood.getID()));

        return foods;
    }

//    private void chooseSearchedFoodForCustomer(){
//        String choice = this.getChoice();
//        if(choice.equals("0"))
//            this.searchFoodForCustomer();
//
//        else {
//            setCurrentFood(controller.handleChooseFood(choice));
//            this.handleShowFoodOptionForCustomer();
//        }
//    }


    public ArrayList<String> searchRestaurant(String searched) {
        ArrayList<String> restaurants = new ArrayList<>();

        String choice = searched ;
        ArrayList<Restaurant> allSearchedRestaurants = this.controller.handleSearchRestaurants(choice);
        for (Restaurant allSearchedRestaurant : allSearchedRestaurants)
            restaurants.add(String.valueOf(allSearchedRestaurant.getRestaurantID()));

        return restaurants;
    }
  //  private void chooseSearchedRestaurantForCustomer(){
        String choice = this.getChoice();
//        if(choice.equals("0"))
//            this.searchRestaurant();
//
//        else {
//            setCurrentRestaurant(controller.handleChooseRestaurant(choice));
//            this.handleShowRestaurantOptionForCustomer();
//        }
   // }

    public static void handleDisplayRestaurantComment(ArrayList<String> showCommentWithResponse ){
        for (int i = 0; currentRestaurant.getAllComments().size()>i; i++){
            showCommentWithResponse.add(3*i , currentRestaurant.getAllComments().get(i).getCommentID() + " :");
            showCommentWithResponse.add(3*i+1 , Customer.getUserByUserID(currentRestaurant.getAllComments()
                    .get(i).getCustomerID()).getUsername()
                    + " :" +  currentRestaurant.getAllComments().get(i).getComment());
            if (currentRestaurant.getAllComments().get(i).isResponseExists())
                showCommentWithResponse.add(3*i+2 ,"manager's response : " + currentRestaurant.getAllComments().get(i).getResponse() );
            else
                showCommentWithResponse.add(3*i+2 ,"manager's response : "  );

        }

    }

    public static void handleAddNewCommentForRestaurant(String comment){
        User loggedInUser = Menu.getLoggedInUser();

        getCurrentRestaurant().addComment(loggedInUser.getUserId() , comment);
    }

    public static boolean handleEditRestaurantCommentForCustomer(String comment , int ID){
        User loggedInUser = Menu.getLoggedInUser();

        if(currentRestaurant.getAllComments().get(ID).getCustomerID() == loggedInUser.getUserId()) {
            currentRestaurant.editComment(loggedInUser.getUserId(), comment);
            return true;
        }
        return false;
    }

    public static void handleDisplayRatingForCustomer(double rate){
       rate = currentRestaurant.getFinalRate();
    }
    public static void handleSubmitRatingForCustomer(double rate){
        User loggedInUser = Menu.getLoggedInUser();

        currentRestaurant.addRate(loggedInUser.getUserId() ,rate);
    }

    public static void handleEditRestaurantRatingForCustomer(double rate){
        User loggedInUser = Menu.getLoggedInUser();

        currentRestaurant.editRate(loggedInUser.getUserId() ,rate);
    }

    public static void handleAccessOrderHistoryForCustomer(ArrayList<String> ID){
        User loggedInUser = Menu.getLoggedInUser();

        for(int i=0 ; i<Order.getAllOrders().size() ; i++)
            if(Order.getAllOrders().get(i).getCustomerID()==loggedInUser.getUserId()){
                ID.add(String.valueOf(Order.getAllOrders().get(i).getOrderID()));
            }
    }

    public static void handleDisplayCartStatusForCustomer(ArrayList<String> foods){
        if(currentCart!=null) {
            for (int i = 0; i < currentCart.getChosenFoods().size(); i++)
                foods.add(currentCart.getChosenFoods().get(i).getName());
        }
    }

    public Message handleConfirmOrderForCustomer(int node) throws IOException {
        if(!haveDelivery() && isDelivery){
            isDelivery = false;
            period = 0;
            startTime = null;
        }
        if(currentCart.getChosenFoods().size()==0 || currentCart==null)
            return Message.INVALID_CHOICE;
        else {
            Customer loggedInUser = (Customer) Menu.getLoggedInUser();
            if(currentOrder.getFinalPrice()>=loggedInUser.getCharge()) {
                return Message.INVALID_ROLE;
            }
            else {
                int destination = node;
                currentDelivery = new Delivery(currentRestaurant.getLocationNode(), destination);

                isDelivery = true;
                period = (int) (currentDelivery.shortestDistinction() * 10);
                this.timer(period);
                currentOrder = new Order(currentCart,period,LocalDateTime.now(),destination);
                currentCart = null;

                return Message.SUCCESS;
            }
        }
    }
    public LocalDateTime handleShowEstimatedDeliveryTime(){
        if(!haveDelivery() && isDelivery){
            isDelivery = false;
            period = 0;
            startTime = null;
        }
        if(!this.haveDelivery()) {
            return null;
        }else {
            return currentOrder.getStartTime().plusSeconds(currentOrder.getEstimatedTime());
        }
    }
    public void timer (int timePeriod){
        this.startTime = LocalDateTime.now();
        this.period = timePeriod * 60;
    }
    public boolean haveDelivery(){
        if (!isDelivery)
            return false;
        return LocalDateTime.now().isBefore(startTime.plusSeconds(period));
    }

    public void handleChargeAccountForCustomer(double charging){

        double charge = charging;
        User loggedInUser = Menu.getLoggedInUser();

        Customer user = (Customer) User.getUserByUserID(loggedInUser.getUserId());
        user.setCharge(charge);

    }

    public double handleDisplayAccountChargeForCustomer(){
        User loggedInUser = Menu.getLoggedInUser();

        Customer user = (Customer) User.getUserByUserID(loggedInUser.getUserId());
        double charge = 0;
        if (user != null) {
            charge = user.getCharge();
        }
        return charge;
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
