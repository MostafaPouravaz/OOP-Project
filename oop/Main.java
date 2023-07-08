import views.RegisterMenu;
//file map order va oon moshkele
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello\nWelcome to DelFood!");
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}