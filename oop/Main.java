import views.RegisterMenu;
//file map order va oon problem
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello\nWelcome to MalFood!");
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}