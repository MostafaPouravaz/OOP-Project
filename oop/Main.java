import views.RegisterMenu;

import java.io.File;

//maybe we have a problem in files
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello\nWelcome to MalFood!");
        new File("oop\\\\files\\\\vendors.json");
        new File("oop\\\\files\\\\customers.json");
        new File("oop\\\\files\\\\restaurants.json");
        new File("oop\\\\files\\\\foods.json");
        new File("oop\\\\files\\\\orders.json");
        new File("oop\\\\files\\\\carts.json");
        new File("oop\\\\files\\\\foodComment.json");
        new File("oop\\\\files\\\\restaurantComments.json");
        new File("oop\\\\files\\\\foodRatings.json");
        new File("oop\\\\files\\\\restaurantRatings.json");
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}