package views;

import com.example.phase3.HelloApplication;
import controllers.RegisterController;
import enums.Message;
import models.User;

import java.io.IOException;

public class RegisterMenu extends Menu{
    private static RegisterMenu instance = null;

    private RegisterController controller;

    // Singleton Pattern
    private RegisterMenu() {
        this.controller = RegisterController.getInstance();
    }

    // Singleton  Pattern
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
            case "3", "exit" -> this.exit();
            default -> System.out.println(Message.INVALID_CHOICE);
        }

    }

    private void exit() {
        Menu.getScanner().close();
    }

    public Message login(String name, String pass) {
        return controller.handleLogin(name, pass);
    }

    private Message register(String type , String name , String pass , String againPass ,String animal) throws IOException {
        Message message = null ;

        if (type.equals("vendor")) {
            message = this.registerVendor(name , pass , againPass,animal );
        } else if (type.equals("customer")) {
            message = this.registerCustomer(name , pass , againPass , animal);
        }
        return message;
    }

    public Message registerCustomer(String name , String pass , String againPass , String animal) throws IOException {
        Message message = this.controller.handleCreateCustomer(name, pass, againPass , animal);
        if (message==Message.SUCCESS){
            User user = User.getUserByUsername(name);
            Menu.setLoggedInUser(user);
        }
        return message;
    }

    public Message registerVendor(String name , String pass , String againPass , String animal) throws IOException {
        Message message = this.controller.handleCreateVendor(name, pass, againPass , animal);
        if (message==Message.SUCCESS){
            User user = User.getUserByUsername(name);
            Menu.setLoggedInUser(user);
        }
        return message;
    }

    public Message forgetPassword( String username , String animalName) {
        Message message = this.controller.handleForgetPassword(username, animalName);

        return message;
    }
    public Message changePassword(String username , String password , String repeatedPassword) {
        Message message = this.controller.handleChangePassword(username, password, repeatedPassword);
        return message;
    }
    @Override
    protected void showOptions() {
        System.out.println("enter one of the options");
        System.out.println("1. register");
        System.out.println("2. login");
        System.out.println("3. exit");

    }
}
