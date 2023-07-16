import views.RegisterMenu;

import java.io.File;

//maybe we have a problem in files
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello\nWelcome to MalFood!");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\vendors.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\customers.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\restaurants.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\ratingForRestaurant.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\commentForRestaurant.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\restaurantFoods.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\restaurantFoodTypes.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\foods.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\ratingForFood.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\commentForFood.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\orders.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\orderFoods.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\chosenFoods.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\carts.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\foodComment.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\restaurantComments.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\foodRatings.json");
        new File("\"C:\\\\Users\\\\Mostafa\\\\IdeaProjects\\\\OOP-Project1\\\\oop\\\\files\\\\restaurantRatings.json");
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}