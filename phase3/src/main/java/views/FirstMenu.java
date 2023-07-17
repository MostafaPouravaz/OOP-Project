package views;

import controllers.FirstController;
import controllers.RegisterController;
import enums.Message;

//menu after main menu
public class FirstMenu extends Menu{
    private static FirstMenu instance = null;

    private FirstController controller;

    private FirstMenu() {
        this.controller = FirstController.getInstance();
    }

    private static void setInstance(FirstMenu instance) {
        FirstMenu.instance = instance;
    }

    public static FirstMenu getInstance() {
        if (FirstMenu.instance == null) {
            FirstMenu.setInstance(new FirstMenu());
        }
        return FirstMenu.instance;
    }
    @Override
    public void run() {
        this.showOptions();

        String choice = this.getChoice();

//        switch (choice) {
//            case "1", "register" -> this.register();
//            case "2", "login" -> this.login();
//            case "3", "exit" -> this.exit();
//            default -> System.out.println(Message.INVALID_CHOICE);
//        }
    }

    @Override
    protected void showOptions() {
        System.out.println("enter one of the options");
        System.out.println("1. register");
        System.out.println("2. login");
        System.out.println("3. exit");
    }
}
