package views;

import models.User;

import java.util.Scanner;

public abstract class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    protected static Scanner getScanner() {
        return Menu.scanner;
    }

    public static User getLoggedInUser() {
        return Menu.loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        Menu.loggedInUser = user;
    }

    protected String getInput(String message) {
        System.out.println(message + ":");
        return Menu.getScanner().nextLine().trim();
    }

    protected String getChoice() {
        return Menu.getScanner().nextLine().trim().toLowerCase();
    }

    public abstract void run();

    protected abstract void showOptions();

}
