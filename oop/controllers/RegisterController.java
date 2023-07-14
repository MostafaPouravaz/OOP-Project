package controllers;

import enums.Message;
import models.Customer;
import models.User;
import models.Vendor;
import views.Menu;

import java.io.IOException;

public class RegisterController extends Controller{
    private static RegisterController instance = null;

    private RegisterController() {

    }

    private static void setInstance(RegisterController instance) {
        RegisterController.instance = instance;
    }

    public static RegisterController getInstance() {
        if (RegisterController.instance == null) {
            RegisterController.setInstance(new RegisterController());
        }
        return RegisterController.instance;
    }

    private Boolean doesUsernameExist(String username) {
        return User.getUserByUsername(username) != null;
    }

    private Boolean isAlphaNumeric(String password) {
        return password.matches("[a-zA-Z0-9]+");
    }

    private Message validatePassword(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword))
            return Message.MISMATCH_PASSWORD;
        if (password.length() < 5)
            return Message.SHORT_PASSWORD;
        if (password.length() > 10)
            return Message.LONG_PASSWORD;
        if (!this.isAlphaNumeric(password))
            return Message.NON_ALPHA_NUMERIC_PASSWORD;

        return Message.SUCCESS;

    }

    public Message handleLogin(String username, String password) {
        User user = User.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Menu.setLoggedInUser(user);
            return Message.SUCCESS;
        }
        return Message.WRONG_CREDENTIALS;

    }
    public Message handleForgetPassword(String username, String animalName) {
        User user = User.getUserByUsername(username);

        if (user != null && user.getAnimalName().equals(animalName)) {
            return Message.SUCCESS;
        }
        return Message.WRONG_CREDENTIALS;
    }
    public Message handleChangePassword(String username, String password, String repeatedPassword) {
        User user = User.getUserByUsername(username);

        if (user == null ) {
            return Message.USER_DONT_EXIST;
        }
        Message message;
        if ((message = this.validatePassword(password, repeatedPassword)) != Message.SUCCESS) {
            return message;
        }
        user.setPassword(password);
        return Message.SUCCESS;

    }

    public Message handleCreateCustomer(String username, String password, String repeatedPassword, String animalName) throws IOException {
        if (this.doesUsernameExist(username)) {
            return Message.USER_EXIST;
        }
        Message message;
        if ((message = this.validatePassword(password, repeatedPassword)) != Message.SUCCESS) {
            return message;
        }
        new Customer(username, password,animalName);
        return Message.SUCCESS;
    }

    public Message handleCreateVendor(String username, String password, String repeatedPassword, String animalName) throws IOException {
        if (this.doesUsernameExist(username)) {
            return Message.USER_EXIST;
        }
        Message message;
        if ((message = this.validatePassword(password, repeatedPassword)) != Message.SUCCESS) {
            return message;
        }
        new Vendor(username, password, animalName);
        return Message.SUCCESS;
    }
}
