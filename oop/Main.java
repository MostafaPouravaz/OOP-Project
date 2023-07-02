import views.RegisterMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello\nWelcome to DelFood!");
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}