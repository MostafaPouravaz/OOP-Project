package views;

import controllers.RegisterController;
import enums.Message;

public class RegisterMenu extends Menu{
    private static RegisterMenu instance = null;

    private RegisterController controller;

    // Singleton Pattern
    private RegisterMenu() {
        this.controller = RegisterController.getInstance();
    }

    // Singleton Pattern
    private static void setInstance(RegisterMenu instance) {
        RegisterMenu.instance = instance;
    }

    // Singleton Pattern
    public static RegisterMenu getInstance() {
        if (RegisterMenu.instance == null) {
            RegisterMenu.setInstance(new RegisterMenu());
        }
        return RegisterMenu.instance;
    }

    @Override
    public void run() {
        this.showOptions();

        String choice = this.getChoice();

        switch (choice) {
            case "1", "register" -> this.register();
            case "2", "login" -> this.login();
            case "3", "exit" -> this.exit();
            default -> System.out.println(Message.INVALID_CHOICE);
        }

    }

    private void exit() {
        Menu.getScanner().close();
    }

    private void login() {
        String username = this.getInput("enter username");
        String password = this.getInput("enter password");

        Message message = this.controller.handleLogin(username, password);
        if (message == Message.SUCCESS) {
            MainMenu.getInstance().run();
        } else {
            System.out.println(message);
        }
        this.run();
    }

    private void register() {
        System.out.println("enter register as vendor or customer");
        String choice = this.getChoice();

        if (choice.equals("vendor")) {
            this.registerVendor();
        } else if (choice.equals("customer")) {
            this.registerCustomer();
        } else {
            System.out.println(Message.INVALID_CHOICE);
        }
        this.run();
    }

    private void registerCustomer() {
        String username = this.getInput("enter username");
        String password = this.getInput("enter password");
        String repeatedPassword = this.getInput("repeat password");

        Message message = this.controller.handleCreateCustomer(username, password, repeatedPassword);
        System.out.println(message == Message.SUCCESS ? "customer registered successfully" : message);
    }

    private void registerVendor() {
        String username = this.getInput("enter username");
        String password = this.getInput("enter password");
        String repeatedPassword = this.getInput("repeat password");
        Message message = this.controller.handleCreateVendor(username, password, repeatedPassword);
        System.out.println(message == Message.SUCCESS ? "vendor registered successfully" : message);
    }

    @Override
    protected void showOptions() {
        System.out.println("enter one of the options");
        System.out.println("1. register");
        System.out.println("2. login");
        System.out.println("3. exit");

    }
}
