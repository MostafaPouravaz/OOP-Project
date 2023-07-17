package com.example.phase3;
import enums.FoodType;
import enums.Message;
import models.*;
import views.MainMenu;
import views.Menu;
import views.RegisterMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HelloApplication {

    public static void showLoginRegisterUI() {
        JLabel titleLabel = new JLabel("Welcome to Melfood");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel ErrorLabel = new JLabel("");
        String userNameError = "Type your user name";
        String passwordError = "Type your password";
        String userTypeError = "Select your type";
        JLabel passwordLabel = new JLabel("Password:");
        JLabel userTypeLabel = new JLabel("Are you a vendor or a customer?");
        JTextField usernameField = new JTextField(20) ;
        JPasswordField passwordField = new JPasswordField(20);
        JRadioButton vendorRadioButton = new JRadioButton("Vendor");
        JRadioButton customerRadioButton = new JRadioButton("Customer");
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        JButton forgetPasswordButton = new JButton("forget password");

        JFrame frame = new JFrame("Login/Register System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(userTypeLabel);
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(vendorRadioButton);
        userTypeGroup.add(customerRadioButton);
        centerPanel.add(vendorRadioButton );
        centerPanel.add(customerRadioButton);
        centerPanel.add(ErrorLabel);
        ErrorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 4 , 10 ,10));
        bottomPanel.add(loginButton);
        bottomPanel.add(registerButton);
        bottomPanel.add(forgetPasswordButton);
        bottomPanel.add(exitButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String userType = null;

            if(username.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(userNameError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(password.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(passwordError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else {
                frame.setVisible(false);

                if (vendorRadioButton.isSelected())
                    userType = "Vendor";
                else if (customerRadioButton.isSelected())
                    userType = "Customer";

                RegisterMenu registerMenu = RegisterMenu.getInstance();
                Message message = registerMenu.login(username , password);

                if (userType != null) {
                    if(message == Message.SUCCESS) {
                        User loggedInUser = Menu.getLoggedInUser();

                        if (userType.equals("Vendor") && loggedInUser instanceof Vendor) {
                            showMenuForVendorUI();
                        } else if(userType.equals("Customer") && loggedInUser instanceof Customer) {
                            showMenuForCustomerUI();
                        }
                        else{
                            ErrorLabel.setVisible(true);
                            ErrorLabel.setText(String.valueOf(Message.USER_DONT_EXIST));
                            ErrorLabel.setForeground(Color.RED);
                            usernameField.setText("");
                            passwordField.setText("");
                            frame.setVisible(true);
                        }
                    }
                    else{
                        ErrorLabel.setVisible(true);
                        ErrorLabel.setText(String.valueOf(Message.USER_DONT_EXIST));
                        ErrorLabel.setForeground(Color.RED);
                        usernameField.setText("");
                        passwordField.setText("");
                        frame.setVisible(true);
                    }
                }
                else {
                    ErrorLabel.setVisible(true);
                    ErrorLabel.setText(userTypeError);
                    ErrorLabel.setForeground(Color.RED);
                    frame.setVisible(true);
                }
            }
        });

        registerButton.addActionListener(e ->{
            frame.setVisible(false);
            showRegisterUI();
        });

        exitButton.addActionListener(e -> System.exit(0));

        forgetPasswordButton.addActionListener(e -> {
            frame.setVisible(false);
            showForgetPasswordUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showForgetPasswordUI() {
        JLabel titleLabel = new JLabel("forget password");
        JLabel ErrorLabel = new JLabel("");
        String userNameError = "Type your user name";
        String animalError = "Type your favorite animal";
        String userTypeError = "Select your type";
        JLabel usernameLabel = new JLabel("Username:");
        JLabel favoriteAnimalLabel = new JLabel("Favorite animal:");
        JLabel userTypeLabel = new JLabel("Are you a vendor or a customer?");
        JTextField usernameField = new JTextField(20);
        JTextField favoriteAnimalField = new JTextField(20);
        JRadioButton vendorRadioButton = new JRadioButton("Vendor");
        JRadioButton customerRadioButton = new JRadioButton("Customer");
        JButton nextButton = new JButton("Next");
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Forget Password System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(favoriteAnimalLabel);
        centerPanel.add(favoriteAnimalField);
        centerPanel.add(userTypeLabel);
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(vendorRadioButton);
        userTypeGroup.add(customerRadioButton);
        centerPanel.add(vendorRadioButton);
        centerPanel.add(customerRadioButton);
        centerPanel.add(ErrorLabel);
        ErrorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 4 , 10 ,10));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String username = usernameField.getText();
            String animal = favoriteAnimalField.getText();
            String userType = null;

            if(username.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(userNameError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(animal.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(animalError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else {
                if (vendorRadioButton.isSelected())
                    userType = "Vendor";
                else if (customerRadioButton.isSelected())
                    userType = "Customer";

                if (userType != null) {
                    RegisterMenu registerMenu = RegisterMenu.getInstance();
                    Message message = registerMenu.forgetPassword(username , animal);
                    if(message == Message.SUCCESS){
                        User loggedInUser = Menu.getLoggedInUser();

                        if (userType.equals("Vendor") && loggedInUser instanceof Vendor) {
                            frame.setVisible(false);
                            showNewPasswordUI(username);
                        } else if(userType.equals("Customer") && loggedInUser instanceof Customer) {
                            frame.setVisible(false);
                            showNewPasswordUI(username);
                        }
                        else{
                            ErrorLabel.setVisible(true);
                            ErrorLabel.setText(String.valueOf(Message.USER_DONT_EXIST));
                            ErrorLabel.setForeground(Color.RED);
                            usernameField.setText("");
                            favoriteAnimalField.setText("");
                            frame.setVisible(true);
                        }
                    }
                    else{
                        ErrorLabel.setVisible(true);
                        ErrorLabel.setText(String.valueOf(message));
                        ErrorLabel.setForeground(Color.RED);
                        frame.setVisible(true);
                    }
                }
                else {
                    ErrorLabel.setVisible(true);
                    ErrorLabel.setText(userTypeError);
                    ErrorLabel.setForeground(Color.RED);
                    frame.setVisible(true);
                }
            }

        });

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showLoginRegisterUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showNewPasswordUI(String userName){
        JLabel titleLabel = new JLabel("new password");
        JLabel ErrorLabel = new JLabel("");
        String passwordError = "Type your password";
        String againPasswordError = "Confirm your password";
        JLabel passwordLabel = new JLabel("New password:");
        JLabel againPasswordLabel = new JLabel("confirm the password:");
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField againPasswordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Login/Register System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(passwordLabel );
        centerPanel.add(passwordField );
        centerPanel.add(againPasswordLabel );
        centerPanel.add(againPasswordField );
        centerPanel.add(registerButton );
        centerPanel.add(backButton );
        centerPanel.add(ErrorLabel);
        ErrorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String againPassword = new String(againPasswordField.getPassword());

            if(password.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(passwordError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(againPassword.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(againPasswordError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else{

                RegisterMenu registerMenu = RegisterMenu.getInstance();
                Message message = registerMenu.changePassword(userName ,password , againPassword);

                if(message == Message.SUCCESS){

                    JLabel okLabel = new JLabel("Changed successfully" );
                    okLabel.setFont(new Font("Arial", Font.BOLD, 15));
                    okLabel.setForeground(Color.BLUE);
                    JButton loginButton = new JButton("Login");

                    JFrame frame1 = new JFrame("Forget Password System");
                    frame1.setSize(250, 150);
                    frame1.setResizable(false);
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    frame1.setLayout(new BorderLayout());
                    JPanel topPanel1 = new JPanel();
                    topPanel1.add(okLabel);
                    frame1.add(topPanel1, BorderLayout.NORTH);

                    JPanel bottomPanel = new JPanel();
                    bottomPanel.add(loginButton);
                    frame1.add(bottomPanel , BorderLayout.SOUTH);

                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                    frame.setVisible(false);

                    loginButton.addActionListener(e1 -> {
                        frame1.setVisible(false);
                        showLoginRegisterUI();
                    });

                }
                else{
                    ErrorLabel.setVisible(true);
                    ErrorLabel.setText(String.valueOf(message));
                    ErrorLabel.setForeground(Color.RED);
                    frame.setVisible(true);
                }
            }


        });

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showForgetPasswordUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showRegisterUI(){
        JLabel titleLabel = new JLabel("Register");
        JLabel ErrorLabel = new JLabel("");
        String userNameError = "Type your user name";
        String userError = "The user doesn't exists";
        String passwordError = "Type your password";
        String againPasswordError = "Confirm your password";
        String animalError = "Type your favorite animal";
        String userTypeError = "Select your type";
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel againPasswordLabel = new JLabel("Confirm the password:");
        JLabel favoriteAnimalLabel = new JLabel("Favorite animal:");
        JLabel userTypeLabel = new JLabel("Are you a vendor or a customer?");
        JTextField usernameField = new JTextField(20) ;
        JTextField favoriteAnimalField = new JTextField(20) ;
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField againPasswordField = new JPasswordField(20);
        JRadioButton vendorRadioButton = new JRadioButton("Vendor");
        JRadioButton customerRadioButton = new JRadioButton("Customer");
        JButton nextButton = new JButton("Next");
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Login/Register System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 2));
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(againPasswordLabel);
        centerPanel.add(againPasswordField);
        centerPanel.add(favoriteAnimalLabel);
        centerPanel.add(favoriteAnimalField);
        centerPanel.add(userTypeLabel);
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(vendorRadioButton);
        userTypeGroup.add(customerRadioButton);
        centerPanel.add(vendorRadioButton );
        centerPanel.add(customerRadioButton);
        centerPanel.add(ErrorLabel);
        ErrorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2 , 10 ,10));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showLoginRegisterUI();
        });

        nextButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String againPassword = new String(againPasswordField.getPassword());
            String animal = favoriteAnimalField.getText();
            String userType = null ;

            if(username.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(userNameError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(password.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(passwordError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(againPassword.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(againPasswordError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else if(animal.equals("")){
                ErrorLabel.setVisible(true);
                ErrorLabel.setText(animalError);
                ErrorLabel.setForeground(Color.RED);
                frame.setVisible(true);
            }
            else {
                frame.setVisible(false);

                if (vendorRadioButton.isSelected())
                    userType = "Vendor";
                else if (customerRadioButton.isSelected())
                    userType = "Customer";

                if (userType != null) {
                    RegisterMenu registerMenu = RegisterMenu.getInstance();

                    if(userType.equals("Vendor")){
                        try {
                                    Message message = registerMenu.registerVendor(username , password , againPassword , animal);
                                    if(message == Message.SUCCESS){
                                        JLabel okLabel = new JLabel("Registered successfully" );
                                        okLabel.setFont(new Font("Arial", Font.BOLD, 15));
                                        okLabel.setForeground(Color.BLUE);
                                        JButton loginButton = new JButton("Login");

                                        JFrame frame1 = new JFrame("Register System");
                                        frame1.setSize(250, 150);
                                        frame1.setResizable(false);
                                        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                        frame1.setLayout(new BorderLayout());
                                        JPanel topPanel1 = new JPanel();
                                        topPanel1.add(okLabel);
                                        frame1.add(topPanel1, BorderLayout.NORTH);

                                        JPanel bottomPanel1 = new JPanel();
                                        bottomPanel1.add(loginButton);
                                        frame1.add(bottomPanel1 , BorderLayout.SOUTH);

                                        frame1.setLocationRelativeTo(null);
                                        frame1.setVisible(true);
                                        frame.setVisible(false);

                                        loginButton.addActionListener(e1 -> {
                                            frame1.setVisible(false);
                                            showLoginRegisterUI();
                                        });
                            }
                            else {
                                ErrorLabel.setVisible(true);
                                ErrorLabel.setText(String.valueOf(message));
                                ErrorLabel.setForeground(Color.RED);
                                frame.setVisible(true);
                            }
                        }
                        catch (IOException ex) {
                            frame.setVisible(true);
                        }
                    }
                    else{
                        try {
                            Message message = registerMenu.registerCustomer(username , password , againPassword , animal);
                            if(message == Message.SUCCESS){
                                JLabel okLabel = new JLabel("Registered successfully" );
                                okLabel.setFont(new Font("Arial", Font.BOLD, 15));
                                okLabel.setForeground(Color.BLUE);
                                JButton loginButton = new JButton("Login");

                                JFrame frame1 = new JFrame("Register System");
                                frame1.setSize(250, 150);
                                frame1.setResizable(false);
                                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                frame1.setLayout(new BorderLayout());
                                JPanel topPanel1 = new JPanel();
                                topPanel1.add(okLabel);
                                frame1.add(topPanel1, BorderLayout.NORTH);

                                JPanel bottomPanel1 = new JPanel();
                                bottomPanel1.add(loginButton);
                                frame1.add(bottomPanel1 , BorderLayout.SOUTH);

                                frame1.setLocationRelativeTo(null);
                                frame1.setVisible(true);
                                frame.setVisible(false);

                                loginButton.addActionListener(e1 -> {
                                    frame1.setVisible(false);
                                    showLoginRegisterUI();
                                });
                            }
                            else {
                                ErrorLabel.setVisible(true);
                                ErrorLabel.setText(String.valueOf(message));
                                ErrorLabel.setForeground(Color.RED);
                                frame.setVisible(true);
                            }
                        }
                        catch (IOException ex) {
                            frame.setVisible(true);
                        }
                    }
                }
                else {
                    ErrorLabel.setVisible(true);
                    ErrorLabel.setText(userTypeError);
                    ErrorLabel.setForeground(Color.RED);
                    frame.setVisible(true);
                }
            }
        });
    }
    public static void showMenuForVendorUI(){
        JLabel titleLabel = new JLabel("Choose the restaurant");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(Color.BLACK);
        JButton backButton = new JButton("Back");
        JButton addNewRestaurantButton = new JButton("Add new restaurant");

        JFrame frame = new JFrame("Menu");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<String> restaurantsName = new ArrayList<>();
        for(int i=0 ; i<Restaurant.getAllRestaurant().size() ; i++)
            if(Restaurant.getAllRestaurant().get(i).getID_Owner() == MainMenu.getLoggedInUser().getUserId())
                restaurantsName.add(Restaurant.getAllRestaurant().get(i).getName());

        String[] array = restaurantsName.toArray(new String[restaurantsName.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {

            String selectedValue = list.getSelectedValue();

            MainMenu.setCurrentRestaurant(Restaurant.getRestaurantByRestaurantName(selectedValue , MainMenu.getLoggedInUser().getUserId()));

            frame.setVisible(false);
            showRestaurantOptions();
        });
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel , BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(1 , 10 , 10 ,10));
        centerPanel.add(addNewRestaurantButton);
        centerPanel.add(backButton);
        frame.add(centerPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showLoginRegisterUI();
        });

        addNewRestaurantButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage1UI();
        });
    }
    public static void showAddRestaurantPage1UI(){
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");
        JLabel titleLabel = new JLabel("New Restaurant");
        JLabel errorLabel = new JLabel("");
        String nameError = "type the restaurant name";
        JLabel nameLabel = new JLabel("Name :");
        JTextField nameField = new JTextField(20) ;

        JFrame frame = new JFrame("New Restaurant System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showMenuForVendorUI();
        });

        nextButton.addActionListener(e -> {
            if(nameField.getText().equals("")){
                errorLabel.setText(nameError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                frame.setVisible(false);
                String name = nameField.getText();
                showAddRestaurantPage2UI();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddRestaurantPage2UI() {
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");
        JLabel titleLabel = new JLabel("New Restaurant");
        JLabel errorLabel = new JLabel("");
        String locationError = "Type the node location";
        JLabel locationLabel = new JLabel("Node location :");
        JTextField locationField = new JTextField(20) ;

        JFrame frame = new JFrame("New Restaurant System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        centerPanel.add(locationLabel);
        centerPanel.add(locationField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage1UI();
        });

        nextButton.addActionListener(e -> {
            if(locationField.getText().equals("")){
                errorLabel.setText(locationError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                frame.setVisible(false);
                int node = Integer.parseInt(locationField.getText());
                showAddRestaurantPage3UI();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddRestaurantPage3UI(){
        JButton noButton = new JButton("No");
        JButton yesButton = new JButton("Yes");
        JLabel titleLabel = new JLabel("Do you want to add food?");

        JFrame frame = new JFrame("New Restaurant System");
        frame.setSize(250, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2 , 10 ,10));
        bottomPanel.add(noButton);
        bottomPanel.add(yesButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        noButton.addActionListener(e -> {
            frame.setVisible(false);
            showMenuForVendorUI();
        });

        yesButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage4UI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddRestaurantPage4UI(){
        JButton backButton = new JButton("Back");
        JButton fastFoodButton = new JButton("Fast food");
        JButton iranianFoodButton = new JButton("Iranian food");
        JButton seaFoodButton = new JButton("Sea food");
        JButton appetizerButton = new JButton("Appetizer");
        JButton otherButton = new JButton("Other");
        JLabel titleLabel = new JLabel("New Restaurant");

        JFrame frame = new JFrame("New Restaurant System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3 , 3 , 10 ,10));
        centerPanel.add(fastFoodButton);
        centerPanel.add(iranianFoodButton);
        centerPanel.add(seaFoodButton);
        centerPanel.add(appetizerButton);
        centerPanel.add(otherButton);
        centerPanel.add(backButton);
        frame.add(centerPanel , BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage3UI();
        });

        fastFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage5UI();
        });

        iranianFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage5UI();
        });

        seaFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage5UI();
        });

        appetizerButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage5UI();
        });

        otherButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage5UI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddRestaurantPage5UI() {
        JTextField nameField = new JTextField(20);
        JLabel titleLabel = new JLabel("New Restaurant");
        JLabel errorLabel = new JLabel("");
        String nameError = "type the food name";
        String priceError = "type the food price";
        JLabel nameLabel = new JLabel("Name :");
        JLabel priceLabel = new JLabel("Price :");
        JTextField priceField = new JTextField(20);
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        JFrame frame = new JFrame("New Restaurant System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel centerPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(priceLabel);
        centerPanel.add(priceField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddRestaurantPage4UI();
        });

        nextButton.addActionListener(e -> {
            if(nameField.getText().equals("")){
                errorLabel.setText(nameError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(priceField.getText().equals("")){
                errorLabel.setText(priceError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                frame.setVisible(false);
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());
                showAddRestaurantPage3UI();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showRestaurantOptions(){
        JLabel titleLabel = new JLabel("Choose one of the options :");
        JButton backButton = new JButton("Back");
        JButton showLocationButton = new JButton("location");
        JButton editLocationButton = new JButton("Edit location");
        JButton showAndAddFoodTypesButton = new JButton("Food type");
        JButton editFoodTypeButton = new JButton("Edit FoodType");
        JButton displayRatingsButton = new JButton("Rating");
        JButton displayCommentsButton = new JButton("Comments");
        JButton showOrderHistoryButton = new JButton("Order history");
        JButton addFoodButton = new JButton("Open orders");
        JButton menuButton = new JButton("Menu");

        JFrame frame = new JFrame("System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4 , 3));
        centerPanel.add(showLocationButton);
        centerPanel.add(editLocationButton);
        centerPanel.add(showAndAddFoodTypesButton);
        centerPanel.add(editFoodTypeButton);
        centerPanel.add(displayRatingsButton);
        centerPanel.add(displayCommentsButton);
        centerPanel.add(showOrderHistoryButton);
        centerPanel.add(addFoodButton);
        centerPanel.add(menuButton);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showMenuForVendorUI();
        });

        menuButton.addActionListener(e -> {
            frame.setVisible(false);
            showMenuUI();
        });

        addFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showOpenOrdersForVendor();
        });

        showOrderHistoryButton.addActionListener(e -> {
            frame.setVisible(false);
            showOrderHistoryForVendor();
        });

        displayCommentsButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayCommentsForVendorUI();
        });

        displayRatingsButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayRatingsForVendorUI();
        });

        editFoodTypeButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditFoodTypeUI();
        });

        showAndAddFoodTypesButton.addActionListener(e -> {
            frame.setVisible(false);
            showAndAddFoodTypesUI();
        });

        editLocationButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditLocationForVendorUI();
        });

        showLocationButton.addActionListener(e -> {
            frame.setVisible(false);
            showRestaurantLocationForVendorUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showMenuUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Foods : ");
        JButton addFoodButton = new JButton("Add food");

        JFrame frame = new JFrame("Food System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel , BorderLayout.NORTH);

        // show foods:
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();

            frame.setVisible(false);
            showFoodOptionForVendorUI();
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(addFoodButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

        addFoodButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showAddRestaurantPage3UI();
        });
    }
    public static void showFoodOptionForVendorUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Options : ");
        JButton editFoodNameButton = new JButton("Edit food name");
        JButton editPriceButton = new JButton("Edit price");
        JButton deleteFoodButton = new JButton("Delete food");
        JButton foodActivationButton = new JButton("Food activation");
        JButton discountActivationButton = new JButton("Discount activation");
        JButton displayCommentsButton = new JButton("Display comments");
        JButton displayRatingsButton = new JButton("Display ratings");

        JFrame frame = new JFrame("Food System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel , BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5 , 2));
        centerPanel.add(editFoodNameButton);
        centerPanel.add(editPriceButton);
        centerPanel.add(deleteFoodButton);
        centerPanel.add(foodActivationButton);
        centerPanel.add(discountActivationButton);
        centerPanel.add(displayCommentsButton);
        centerPanel.add(displayRatingsButton);
        centerPanel.add(backButton);
        frame.add(centerPanel , BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuUI();
        });

        displayRatingsButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showDisplayFoodRatingForVendorUI();
        });

        displayCommentsButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showDisplayFoodCommentsForVendorUI();
        });

        discountActivationButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showDiscountActivationUI();
        });

        foodActivationButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodActivationUI();
        });

        deleteFoodButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showDeleteFoodForVendorUI();
        });

        editPriceButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showEditFoodPriceUI();
        });

        editFoodNameButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showEditFoodNameUI();
        });

    }
    public static void showDisplayFoodRatingForVendorUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("The rating");

        JFrame frame = new JFrame("Food Rating System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();



            frame.setVisible(false);
        });


        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForVendorUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showDisplayFoodCommentsForVendorUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("The comments");

        JFrame frame = new JFrame("Food Comment System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();



            frame.setVisible(false);
            
        });


        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForVendorUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showDiscountActivationUI(){
        JLabel titleLabel = new JLabel("Discount");
        JLabel errorLabel = new JLabel("");
        String discountError = "Enter discount percent";
        String timeError = "Enter timestamp";
        JButton backButton = new JButton("Back");
        JLabel timeStampLabel = new JLabel("Enter timestamp into minutes : ");
        JTextField timeStampField = new JTextField(20) ;
        JButton nextButton = new JButton("Ok");
        JLabel discountLabel = new JLabel("Enter discount percent : ");
        JTextField discountField = new JTextField(20) ;

        JFrame frame = new JFrame("Food System");
        frame.setSize(400, 250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(10 , 2));
        centerPanel.add(timeStampLabel);
        centerPanel.add(timeStampField);
        centerPanel.add(discountLabel);
        centerPanel.add(discountField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForVendorUI();
        });

        nextButton.addActionListener(e -> {
            if(discountField.getText().equals("")){
                errorLabel.setText(discountError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(timeStampField.getText().equals("")){
                errorLabel.setText(timeError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                JLabel successLabel = new JLabel("Done successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");

                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e1 -> {
                    frame1.setVisible(false);
                    showFoodOptionForVendorUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showFoodActivationUI(){
        // the phase 1 needed for verify that is active or no ;
    }
    public static void showDeleteFoodForVendorUI(){
        JLabel successLabel = new JLabel("Deleted successfully");
        JFrame frame = new JFrame("Successful");
        JButton okButton = new JButton("ok");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel = new JPanel();
        centerPanel.add(successLabel);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodOptionForVendorUI();
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showEditFoodPriceUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Edit food price");
        JLabel errorLabel = new JLabel("");
        String nameError = "Type your food price";
        JButton editButton = new JButton("Edit");
        JLabel editPriceLabel = new JLabel("Type price : ");
        JTextField editPriceField = new JTextField(20) ;
        editPriceField.setSize(40 , 20);

        JFrame frame = new JFrame("Food System");
        frame.setSize(400, 250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5 , 2));
        centerPanel.add(editPriceLabel);
        centerPanel.add(editPriceField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(editButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        editButton.addActionListener(e -> {
            if(editPriceField.getText().equals("")){
                errorLabel.setText(nameError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {

                JLabel successLabel = new JLabel("Edited successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e1 -> {
                    frame1.setVisible(false);
                    showFoodOptionForVendorUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showEditFoodNameUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Edit food name");
        JLabel errorLabel = new JLabel("");
        String nameError = "Type your food name";
        JButton editButton = new JButton("Edit");
        JLabel editNameLabel = new JLabel("Type name : ");
        JTextField editNameField = new JTextField(20) ;
        editNameField.setSize(40 , 20);

        JFrame frame = new JFrame("Food System");
        frame.setSize(400, 250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5 , 2));
        centerPanel.add(editNameLabel);
        centerPanel.add(editNameField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(editButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        editButton.addActionListener(e -> {
            if(editNameField.getText().equals("")){
                errorLabel.setText(nameError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {

                JLabel successLabel = new JLabel("Edited successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e1 -> {
                    frame1.setVisible(false);
                    showFoodOptionForVendorUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showOpenOrdersForVendor(){
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Order System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();

        });


        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

    }
    public static void showOrderHistoryForVendor(){
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Order System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();

        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

    }
    public static void showDisplayCommentsForVendorUI(){
        JButton backButton = new JButton("Back");
        JButton responseButton = new JButton("Response");

        JFrame frame = new JFrame("Comment System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();

            responseButton.addActionListener(e1 -> {
                frame.setVisible(false);
                showResponseToComment(selectedIndex); // it can also be selectedValue ;
            });

        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(responseButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

    }
    public static void showResponseToComment(int index){
        int selectedIndex = index;
        // TODO verify the ID comment and then :
        JLabel titleLabel = new JLabel("Responding");
        JLabel successLabel = new JLabel("Done successfully");
        successLabel.setFont(new Font("Arial", Font.BOLD, 12));
        successLabel.setForeground(Color.BLUE);
        JButton backButton = new JButton("Back");
        JButton okButton = new JButton("Ok");
        JButton nextButton = new JButton("Next");
        JLabel responseLabel = new JLabel("Type your response :");
        JTextField responseField = new JTextField(20) ;

        JFrame frame = new JFrame("Responding System");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(responseLabel);
        centerPanel.add(responseField);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1 , 2));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showDisplayCommentsForVendorUI();
        });

        nextButton.addActionListener(e1 -> {
            String response = responseField.getText();
            //Todo after saving :

            JFrame frame1 = new JFrame("Successful");
            frame1.setSize(250, 150);
            frame1.setResizable(false);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel centerPanel1 = new JPanel();
            centerPanel1.add(successLabel);
            frame1.add(centerPanel1 , BorderLayout.CENTER);

            JPanel bottomPanel1 = new JPanel();
            bottomPanel1.add(okButton);
            frame1.add(bottomPanel1, BorderLayout.SOUTH);

            okButton.addActionListener(e -> {
                frame1.setVisible(false);
                showDisplayCommentsForVendorUI();
            });
            frame.setVisible(false);
            frame1.setLocationRelativeTo(null);
            frame1.setVisible(true);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayRatingsForVendorUI(){
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Rating System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });
    }
    public static void showEditFoodTypeUI(){
        JButton backButton = new JButton("Back");   

        JFrame frame = new JFrame("Food Type System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" , "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {
            // Get the selected index and value
            int selectedIndex = list.getSelectedIndex();
            String selectedValue = list.getSelectedValue();

            // TODO now with given Index we find the selected restaurant and show this window :
            frame.setVisible(false);
            showAddRestaurantPage3UI();

        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });
    }
    public static void showAndAddFoodTypesUI(){
        JButton backButton = new JButton("Back");
        JButton addButton = new JButton("Add food type");
        JFrame frame = new JFrame("Food Type System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<String> foodTypesName = new ArrayList<>();
        for(int i=0 ; i<MainMenu.getCurrentRestaurant().getFoodType().size() ; i++)
            foodTypesName.add(FoodType.getFoodTypeNameWithFromInt(MainMenu.getCurrentRestaurant().getFoodType().get(i)));

        String[] array = foodTypesName.toArray(new String[foodTypesName.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 ,2));
        bottomPanel.add(backButton);
        bottomPanel.add(addButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

        addButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showAddRestaurantPage3UI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEditLocationForVendorUI(){
        JLabel titleLabel = new JLabel("Editing");
        JLabel successLabel = new JLabel("Edited successfully");
        JLabel errorLabel = new JLabel("");
        String nodeError = "Type your location node";
        successLabel.setFont(new Font("Arial", Font.BOLD, 12));
        successLabel.setForeground(Color.BLUE);
        JButton backButton = new JButton("Back");
        JButton okButton = new JButton("Ok");
        JButton nextButton = new JButton("Next");
        JLabel nodeLabel = new JLabel("Enter the new node :");
        JTextField nodeField = new JTextField(20) ;

        JFrame frame = new JFrame("Edit Location System");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(nodeLabel);
        centerPanel.add(nodeField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1 , 2));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

        nextButton.addActionListener(e1 -> {
            if(nodeField.getText().equals("")){
                errorLabel.setText(nodeError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                int node = Integer.parseInt(nodeField.getText());

                MainMenu.editLocation(node);
                JFrame frame1 = new JFrame("Successful");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showEditLocationForVendorUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showRestaurantLocationForVendorUI(){
        int node = MainMenu.showLocation();

        JLabel showLocationLabel = new JLabel("The Restaurant location : "+ node );
        showLocationLabel.setFont(new Font("Arial", Font.BOLD, 12));
        showLocationLabel.setForeground(Color.BLUE);
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Restaurant Location System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.add(showLocationLabel);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showRestaurantOptions();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showMenuForCustomerUI(){
        JButton backButton = new JButton("Back");
        JButton searchRestaurantButton = new JButton("Search for restaurant");
        JTextField searchRestaurantField = new JTextField(20) ;
        JButton accessOrderHistoryButton = new JButton("Order history");
        JButton displayCartStatusButton = new JButton("Cart status");
        JButton confirmOrderButton = new JButton("Order");
        JButton chargeAccountButton = new JButton("Charge");
        JButton displayAccountChargeButton = new JButton("Display charge");
        JButton showEstimatedDeliveryTimeButton = new JButton("Delivery time");

        JFrame frame = new JFrame("Menu System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(2 , 1 , 10 ,10));
        topPanel.add(searchRestaurantField);
        topPanel.add(searchRestaurantButton);
        frame.add(topPanel, BorderLayout.NORTH);

        ArrayList<String> RestaurantsName = new ArrayList<>();
        for(int i=0; i< Restaurant.getAllRestaurant().size() ; i++)
            RestaurantsName.add( Restaurant.getAllRestaurant().get(i).getName());

        String[] array1 = RestaurantsName.toArray(new String[RestaurantsName.size()]);

        JList<String> list = new JList<>(array1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {

            String selectedValue = list.getSelectedValue();

            for(int i=0; i< Restaurant.getAllRestaurant().size() ; i++)
                if(Restaurant.getAllRestaurant().get(i).getName().equals( selectedValue))
                    MainMenu.setCurrentRestaurant(Restaurant.getAllRestaurant().get(i));

            frame.setVisible(false);
            showFoodTypeForCustomerUI();
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        bottomPanel.add(accessOrderHistoryButton);
        bottomPanel.add(displayCartStatusButton);
        bottomPanel.add(confirmOrderButton);
        bottomPanel.add(chargeAccountButton);
        bottomPanel.add(displayAccountChargeButton);
        bottomPanel.add(showEstimatedDeliveryTimeButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showLoginRegisterUI();
        });

        searchRestaurantButton.addActionListener(e -> {
            String searched = searchRestaurantField.getText();
            MainMenu mainMenu = MainMenu.getInstance();

            ArrayList<String> restaurants;
            restaurants = mainMenu.searchRestaurant(searched);

            String[] array = restaurants.toArray(new String[restaurants.size()]);


            list.setListData(array);

            frame.revalidate();
            frame.repaint();
        });

        showEstimatedDeliveryTimeButton.addActionListener(e -> {
            frame.setVisible(false);
            showEstimatedDeliveryTimeUI();
        });

        displayAccountChargeButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayAccountChargeUI();
        });


        chargeAccountButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showChargeAccountUI();
        });


        confirmOrderButton.addActionListener(e -> {
            frame.setVisible(false);
            showConfirmOrderButtonUI();
        });


        displayCartStatusButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayCartStatusUI();
            //TODO : must input of this function be cart than later should be added
        });

        accessOrderHistoryButton.addActionListener(e -> {
            frame.setVisible(false);

            showAccessOrderHistoryUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEstimatedDeliveryTimeUI(){
        MainMenu mainMenu = MainMenu.getInstance();
        LocalDateTime deliveryTime = mainMenu.handleShowEstimatedDeliveryTime();
        JLabel errorLabel = new JLabel("");
        String deliveryError = "you have no order";
        JLabel showtimeLabel = new JLabel("The estimated time : " +deliveryTime );

        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Estimated Delivery Time System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(2 , 1));
        centerPanel.add(showtimeLabel);
        showtimeLabel.setVisible(false);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });

        if(deliveryTime==null){
            errorLabel.setText(deliveryError);
            errorLabel.setForeground(Color.RED);
            errorLabel.setVisible(true);
            frame.setVisible(true);
        }
        else {
            showtimeLabel.setVisible(true);
            frame.setVisible(true);
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayAccountChargeUI(){
        MainMenu mainMenu = MainMenu.getInstance();
        double charge = mainMenu.handleDisplayAccountChargeForCustomer();
        JLabel showChargeLabel = new JLabel("User account charge : " + charge);
        showChargeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        showChargeLabel.setForeground(Color.BLUE);
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Display Charge System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.add(showChargeLabel);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showChargeAccountUI(){
        JLabel titleLabel = new JLabel("Charging");
        JLabel errorLabel = new JLabel("");
        String chargeError = "First set the charge amount";
        JLabel successLabel = new JLabel("Charged successfully");
        successLabel.setFont(new Font("Arial", Font.BOLD, 12));
        successLabel.setForeground(Color.BLUE);
        JButton backButton = new JButton("Back");
        JButton okButton = new JButton("Ok");
        JButton nextButton = new JButton("Next");
        JLabel chargeLabel = new JLabel("How much do you want charge your account?");
        JTextField chargeField = new JTextField(20) ;

        JFrame frame = new JFrame("Charge Account System");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(chargeLabel);
        centerPanel.add(chargeField);
        centerPanel.add(errorLabel);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1 , 2));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });

        nextButton.addActionListener(e1 -> {
            if(chargeField.getText().equals("")){
                errorLabel.setText(chargeError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                double charge = Double.parseDouble(chargeField.getText());

                MainMenu mainMenu = MainMenu.getInstance();
                mainMenu.handleChargeAccountForCustomer(charge);
                JFrame frame1 = new JFrame("Successful");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showChargeAccountUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public static void showConfirmOrderButtonUI(){
        JLabel titleLabel = new JLabel("Order");
        JLabel errorLabel = new JLabel("");
        String nodeError = "Type your node address";
        String noFoodError = "There is no any food";
        String noChargeError = "Insufficient inventory";
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");
        JLabel nodeLabel = new JLabel("what is your address(enter your node)?");
        JTextField nodeField = new JTextField(20) ;

        JFrame frame = new JFrame("Confirm Order System");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLayout(new BorderLayout());JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(nodeLabel);
        centerPanel.add(nodeField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1 , 2));
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel , BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });

        nextButton.addActionListener(e1 -> {
            if(nodeField.getText().equals("")){
                errorLabel.setText(nodeError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                int node = Integer.parseInt(nodeField.getText());
                MainMenu mainMenu = MainMenu.getInstance();
                try {
                    Message message = mainMenu.handleConfirmOrderForCustomer(node);
                    if(message==Message.SUCCESS){
                        JLabel successLabel = new JLabel("Charged successfully");
                        successLabel.setFont(new Font("Arial", Font.BOLD, 12));
                        successLabel.setForeground(Color.BLUE);
                        JButton okButton = new JButton("Ok");


                        JFrame frame1 = new JFrame("Successful");
                        frame1.setSize(250, 150);
                        frame1.setResizable(false);
                        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JPanel centerPanel1 = new JPanel();
                        centerPanel1.add(successLabel);
                        frame1.add(centerPanel1, BorderLayout.CENTER);

                        JPanel bottomPanel1 = new JPanel();
                        bottomPanel1.add(okButton);
                        frame1.add(bottomPanel1, BorderLayout.SOUTH);

                        okButton.addActionListener(e -> {
                            frame1.setVisible(false);
                            showMenuForCustomerUI();
                        });
                        frame.setVisible(false);
                        frame1.setLocationRelativeTo(null);
                        frame1.setVisible(true);
                    }
                    else if(message==Message.INVALID_CHOICE){
                        errorLabel.setText(noFoodError);
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setVisible(true);
                        frame.setVisible(true);
                    }
                    else if(message == Message.INVALID_ROLE){
                        errorLabel.setText(noChargeError);
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setVisible(true);
                        frame.setVisible(true);
                    }

                } catch (IOException e) {
                    showMenuForCustomerUI();
                }

            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayCartStatusUI(){
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Cart Status System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<String> foods = new ArrayList<>();
        MainMenu.handleDisplayCartStatusForCustomer(foods);
        String[] array = foods.toArray(new String[foods.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });
    }
    public static void showAccessOrderHistoryUI(){
        JButton backButton = new JButton("Back");

        JFrame frame = new JFrame("Order History System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<String> items = new ArrayList<>();
        MainMenu.handleAccessOrderHistoryForCustomer(items);

        String[] array = items.toArray(new String[items.size()]);
        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {
            String selectedValue = list.getSelectedValue();

            Order order = null;

            for(int i=0 ; i< Order.getAllOrders().size() ; i++)
                if(Order.getAllOrders().get(i).getOrderID() == Integer.parseInt(selectedValue)){
                    order = Order.getAllOrders().get(i);
                    break;
                }
            String restaurantName = null;
            if (order != null) {
                restaurantName = order.getRestaurantName();
            }
            double finalPrice = order != null ? order.getFinalPrice() : 0;

            JButton okButton = new JButton("Ok");
            JButton orderedFoodsButton = new JButton("Ordered food");
            JLabel IDLabel = new JLabel("the order " + selectedValue);
            JLabel restaurantNameLabel = new JLabel("Restaurant name : " + restaurantName);
            JLabel finalPriceLabel = new JLabel("The final price : " + finalPrice);

            JFrame frame1 = new JFrame("Order System");
            frame1.setSize(250, 150);
            frame1.setResizable(false);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel centerPanel = new JPanel(new GridLayout(3 , 1));
            centerPanel.add(IDLabel);
            centerPanel.add(restaurantNameLabel);
            centerPanel.add(finalPriceLabel);
            frame1.add(centerPanel, BorderLayout.NORTH);

            JPanel bottomPanel1 = new JPanel(new GridLayout(1 , 2));
            bottomPanel1.add(okButton);
            bottomPanel1.add(orderedFoodsButton);
            frame.add(bottomPanel1, BorderLayout.SOUTH);

            okButton.addActionListener(e1 -> {
                frame.setVisible(false);
                showAccessOrderHistoryUI();
            });

            Order finalOrder = order;
            orderedFoodsButton.addActionListener(e1 -> {
                frame.setVisible(false);
                frame1.setVisible(false);
                JButton ok2Button = new JButton("Ok");

                JFrame frame2 = new JFrame("Order System");
                frame2.setSize(250, 150);
                frame2.setResizable(false);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ArrayList<String> foods = new ArrayList<>();
                if (finalOrder != null) {
                    for(int i = 0; i< finalOrder.getOrderedFoods().size() ; i++)
                        foods.add(finalOrder.getOrderedFoods().get(i).getName());
                }

                String[] array1 = foods.toArray(new String[foods.size()]);
                JList<String> list1 = new JList<>(array1);
                JScrollPane scrollPane1 = new JScrollPane(list1);
                frame2.add(scrollPane1, BorderLayout.CENTER);

                JPanel bottomPanel = new JPanel();
                bottomPanel.add(ok2Button);
                frame2.add(bottomPanel, BorderLayout.SOUTH);

                ok2Button.addActionListener(e2 -> {
                    frame2.setVisible(false);
                    showMenuForCustomerUI();
                });

                frame2.setVisible(true);


            });


            frame1.setVisible(true);
            frame.setVisible(false);

        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });
    }
    public static void showFoodTypeForCustomerUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Choose the food type");

        JFrame frame = new JFrame("Food Type System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        ArrayList<String> foodTypeName = new ArrayList<>();

        for(int i=0 ; i<MainMenu.getCurrentRestaurant().getFoodType().size() ; i++)
            foodTypeName.add(FoodType.getFoodTypeNameWithFromInt(MainMenu.getCurrentRestaurant().getFoodType().get(i)));

        String[] array = foodTypeName.toArray(new String[foodTypeName.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {

            String selectedValue = list.getSelectedValue();

            int foodTypeInt = FoodType.getFoodTypeIntFromFoodTypeName(selectedValue);
            MainMenu.setCurrentFoodType(FoodType.getFoodTypeFromInt(foodTypeInt));

            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showMenuForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showFoodsForCustomerUI(){
        JButton backButton = new JButton("Back");
        JButton searchFoodButton = new JButton("Search for food");
        JTextField searchFoodField = new JTextField(20) ;
        JButton displayRestaurantCommentsButton = new JButton("Comments");
        JButton addNewCommentForRestaurantButton = new JButton("Add comment");
        JButton editCommentForRestaurantButton = new JButton("Edit comment");
        JButton displayRestaurantRatingButton = new JButton("Rating");
        JButton submitRestaurantRatingButton = new JButton("Submit rating");
        JButton editRestaurantRatingButton = new JButton("Edit rating");

        JFrame frame = new JFrame("Food System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(2 , 1 , 10 ,10));
        topPanel.add(searchFoodField);
        topPanel.add(searchFoodButton);
        frame.add(topPanel, BorderLayout.NORTH);

        ArrayList<String> foodsName = new ArrayList<>();
        int size = MainMenu.getCurrentRestaurant().
                getFoodsWithFoodTypeInt(FoodType.getIntFromFoodType(MainMenu.getCurrentFoodType())).size();

        for(int i=0 ; i<size ; i++)
            foodsName.add(MainMenu.getCurrentRestaurant()
                    .getFoodsWithFoodTypeInt(FoodType.getIntFromFoodType(MainMenu.getCurrentFoodType())).get(i).getName());

        String[] array = foodsName.toArray(new String[foodsName.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(event -> {

            String selectedValue = list.getSelectedValue();

            MainMenu.setCurrentFood(MainMenu.getCurrentRestaurant().getFoodWithName(selectedValue));

            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2 , 4 , 10 ,10));
        bottomPanel.add(displayRestaurantCommentsButton);
        bottomPanel.add(addNewCommentForRestaurantButton);
        bottomPanel.add(editCommentForRestaurantButton);
        bottomPanel.add(displayRestaurantRatingButton);
        bottomPanel.add(submitRestaurantRatingButton);
        bottomPanel.add(editRestaurantRatingButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodTypeForCustomerUI();
        });

        searchFoodButton.addActionListener(e -> {
            String searched = searchFoodField.getText();
            MainMenu mainMenu = MainMenu.getInstance();

            ArrayList<String> foods;
            foods = mainMenu.searchFoodForCustomer(searched);

            String[] array1 = foods.toArray(new String[foods.size()]);


            list.setListData(array1);

            frame.revalidate();
            frame.repaint();
        });

        editRestaurantRatingButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditRestaurantRatingUI();
        });

        submitRestaurantRatingButton.addActionListener(e -> {
            frame.setVisible(false);
            showSubmitRestaurantRatingUI();
        });

        displayRestaurantRatingButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayRestaurantRatingUI();
        });

        editCommentForRestaurantButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditCommentForRestaurantUI();
        });

        displayRestaurantCommentsButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayRestaurantCommentsUI();
        });

        addNewCommentForRestaurantButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddNewCommentForRestaurantUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEditRestaurantRatingUI(){
        JLabel titleLabel = new JLabel("Edit rating");
        JLabel errorLabel = new JLabel("");
        String ratingError = "Type your rate";
        String ratingLimitError = "rate between 0 to 5 ";
        JLabel ratingLabel = new JLabel("please rate (0 to 5) : ");
        JTextField ratingField = new JTextField(20) ;
        JButton backButton = new JButton("Back");
        JButton editButton = new JButton("Edit");

        JFrame frame = new JFrame("Restaurant Rating System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 2));
        centerPanel.add(ratingLabel);
        centerPanel.add(ratingField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(editButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        editButton.addActionListener(e1 -> {
            if(ratingField.getText().equals("")){
                errorLabel.setText(ratingError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(Integer.parseInt(ratingField.getText())>5 || Integer.parseInt(ratingField.getText())<0){
                errorLabel.setText(ratingLimitError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                MainMenu.handleEditRestaurantRatingForCustomer(Integer.parseInt(ratingField.getText()));
                JLabel successLabel = new JLabel("Edited successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");

                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showEditRestaurantRatingUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showSubmitRestaurantRatingUI(){
        JLabel titleLabel = new JLabel("Submit rating");
        JLabel errorLabel = new JLabel("");
        String ratingError = "Type your rate";
        String ratingLimitError = "rate between (0-5) ";
        JLabel ratingLabel = new JLabel("please rate (0 to 5) : ");
        JTextField ratingField = new JTextField(20) ;
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit");

        JFrame frame = new JFrame("Restaurant Rating System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 2));
        centerPanel.add(ratingLabel);
        centerPanel.add(ratingField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(submitButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        submitButton.addActionListener(e1 -> {
            if(ratingField.getText().equals("")){
                errorLabel.setText(ratingError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(Integer.parseInt(ratingField.getText())>5 || Integer.parseInt(ratingField.getText())<0){
                errorLabel.setText(ratingLimitError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                MainMenu.handleSubmitRatingForCustomer(Integer.parseInt(ratingField.getText()));
                JLabel successLabel = new JLabel("Submitted successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");

                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showSubmitRestaurantRatingUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayRestaurantRatingUI(){
        double rate = 0;
        MainMenu.handleDisplayRatingForCustomer(rate);
        JLabel ratingLabel = new JLabel("the rate of restaurant : " + rate);
        JFrame frame = new JFrame("Restaurant Rating System");
        JButton backButton = new JButton("Back");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel1 = new JPanel();
        centerPanel1.add(ratingLabel);
        frame.add(centerPanel1 , BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEditCommentForRestaurantUI(){
        JLabel titleLabel = new JLabel("Edit comment");
        JLabel errorLabel = new JLabel("");
        String commentError = "Type your comment";
        String IDError = "Type your ID comment";
        String IDOwnerError = "You can not edit this comment";
        JButton backButton = new JButton("Back");
        JLabel IDCommentLabel = new JLabel("Enter your comment ID :");
        JTextField IDCommentField = new JTextField(20) ;
        JButton editButton = new JButton("Edit");
        JLabel editCommentLabel = new JLabel("Please type your comment :");
        JTextField editCommentField = new JTextField(20) ;

        JFrame frame = new JFrame("Restaurant Comment System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(10 , 2));
        centerPanel.add(IDCommentLabel);
        centerPanel.add(IDCommentField);
        centerPanel.add(editCommentLabel);
        centerPanel.add(editCommentField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(editButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        editButton.addActionListener(e -> {
            if(editCommentField.getText().equals("")){
                errorLabel.setText(commentError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(IDCommentField.getText().equals("")){
                errorLabel.setText(IDError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {

                boolean bool = MainMenu.handleEditRestaurantCommentForCustomer(editCommentField.getText() , Integer.parseInt(IDCommentField.getText()));

                if(bool) {
                    JLabel successLabel = new JLabel("Edited successfully");
                    JFrame frame1 = new JFrame("Successful");
                    JButton okButton = new JButton("ok");

                    frame1.setSize(250, 150);
                    frame1.setResizable(false);
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel centerPanel1 = new JPanel();
                    centerPanel1.add(successLabel);
                    frame1.add(centerPanel1, BorderLayout.CENTER);

                    JPanel bottomPanel1 = new JPanel();
                    bottomPanel1.add(okButton);
                    frame1.add(bottomPanel1, BorderLayout.SOUTH);

                    okButton.addActionListener(e1 -> {
                        frame1.setVisible(false);
                        showEditCommentForRestaurantUI();
                    });
                    frame.setVisible(false);
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                }
                else {
                    errorLabel.setText(IDOwnerError);
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setVisible(true);
                    frame.setVisible(true);
                }

            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddNewCommentForRestaurantUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Add new comment :");
        JLabel errorLabel = new JLabel("");
        String commentError = "Type your comment";
        JButton addButton = new JButton("Add");
        JLabel addCommentLabel = new JLabel("Add your comment");
        JTextField addCommentField = new JTextField(20) ;
        addCommentField.setSize(40 , 20);

        JFrame frame = new JFrame("Restaurant Comment System");
        frame.setSize(400, 250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5 , 2));
        centerPanel.add(addCommentLabel);
        centerPanel.add(addCommentField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(addButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        addButton.addActionListener(e -> {
            if(addCommentField.getText().equals("")){
                errorLabel.setText(commentError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {

                MainMenu.handleAddNewCommentForRestaurant(addCommentField.getText());
                JLabel successLabel = new JLabel("Added successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e1 -> {
                    frame1.setVisible(false);
                    showAddNewCommentForRestaurantUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayRestaurantCommentsUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("The comments");

        JFrame frame = new JFrame("Restaurant Comment System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);


        ArrayList<String> showCommentWithResponse = new ArrayList<>();
        MainMenu.handleDisplayRestaurantComment(showCommentWithResponse);

        String[] array = showCommentWithResponse.toArray(new String[showCommentWithResponse.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showFoodOptionForCustomerUI(){
        JLabel titleLabel = new JLabel("The Food");
        JButton backButton = new JButton("Back");
        JButton displayCommentsOfFoodButton = new JButton("Display comment's of food ");
        JButton addNewCommentForFoodButton = new JButton("add new comment for food");
        JButton editCommentButton = new JButton("edit comment");
        JButton displayRatingOfFoodButton = new JButton("Display rating of food");
        JButton submitRatingForFoodButton = new JButton("submit rating for food");
        JButton editRatingButton = new JButton("Edit rating");
        JButton AddFoodToCartButton = new JButton("Add food to cart");

        JFrame frame = new JFrame("Food System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(10 , 2));
        centerPanel.add(displayCommentsOfFoodButton);
        centerPanel.add(addNewCommentForFoodButton);
        centerPanel.add(editCommentButton);
        centerPanel.add(displayRatingOfFoodButton);
        centerPanel.add(submitRatingForFoodButton);
        centerPanel.add(editRatingButton);
        centerPanel.add(AddFoodToCartButton);
        centerPanel.add(backButton);
        frame.add(centerPanel , BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodsForCustomerUI();
        });

        AddFoodToCartButton.addActionListener(e -> {
            MainMenu.handleAddFoodToCart();

            JLabel successLabel = new JLabel("Added successfully");
            JFrame frame1 = new JFrame("Successful");
            JButton okButton = new JButton("ok");

            frame1.setSize(250, 150);
            frame1.setResizable(false);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel centerPanel1 = new JPanel();
            centerPanel1.add(successLabel);
            frame1.add(centerPanel1 , BorderLayout.CENTER);

            JPanel bottomPanel1 = new JPanel();
            bottomPanel1.add(okButton);
            frame1.add(bottomPanel1, BorderLayout.SOUTH);

            okButton.addActionListener(e1 -> {
                frame1.setVisible(false);
                showFoodOptionForCustomerUI();
            });
            frame.setVisible(false);
            frame1.setLocationRelativeTo(null);
            frame1.setVisible(true);
        });

        editRatingButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditFoodRatingUI();
        });

        submitRatingForFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showSubmitFoodRatingUI();
        });

        displayRatingOfFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayFoodRatingUI();
        });

        editCommentButton.addActionListener(e -> {
            frame.setVisible(false);
            showEditCommentForFoodUI();
        });

        addNewCommentForFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showAddNewCommentForFoodUI();
        });

        displayCommentsOfFoodButton.addActionListener(e -> {
            frame.setVisible(false);
            showDisplayFoodCommentsUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEditFoodRatingUI(){
        JLabel titleLabel = new JLabel("Edit rating");
        JLabel errorLabel = new JLabel("");
        String ratingError = "Type your rate";
        String ratingLimitError = "rate between (0-5) ";
        JLabel ratingLabel = new JLabel("please rate (0 to 5) : ");
        JTextField ratingField = new JTextField(20) ;
        JButton backButton = new JButton("Back");
        JButton editButton = new JButton("Edit");

        JFrame frame = new JFrame("Food Rating System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 2));
        centerPanel.add(ratingLabel);
        centerPanel.add(ratingField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(editButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        editButton.addActionListener(e1 -> {
            if(ratingField.getText().equals("")){
                errorLabel.setText(ratingError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(Integer.parseInt(ratingField.getText()) > 5 || Integer.parseInt(ratingField.getText()) < 0 ){
                errorLabel.setText(ratingLimitError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                MainMenu.handleEditRatingForFoodForCustomer(Double.parseDouble(ratingField.getText()));

                JLabel successLabel = new JLabel("Edited successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");

                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showEditFoodRatingUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showSubmitFoodRatingUI(){
        JLabel titleLabel = new JLabel("Submit rating");
        JLabel errorLabel = new JLabel("");
        String ratingError = "Type your rate";
        String ratingLimitError = "rate between (0-5) ";
        JLabel ratingLabel = new JLabel("please rate (0 to 5) : ");
        JTextField ratingField = new JTextField(20) ;
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit");

        JFrame frame = new JFrame("Food Rating System");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2 , 2));
        centerPanel.add(ratingLabel);
        centerPanel.add(ratingField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(submitButton);
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        submitButton.addActionListener(e1 -> {
            if(ratingField.getText().equals("")){
                errorLabel.setText(ratingError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(Integer.parseInt(ratingField.getText()) > 5 || Integer.parseInt(ratingField.getText()) < 0 ){
                errorLabel.setText(ratingLimitError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                MainMenu.handleSubmitRatingForFoodForCustomer(Double.parseDouble(ratingField.getText()));

                JLabel successLabel = new JLabel("Submitted successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");

                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                    frame1.setVisible(false);
                    showSubmitFoodRatingUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayFoodRatingUI(){
        double rate =0;
        MainMenu.handleDisplayRatingOfFoodForCustomer(rate);
        JLabel ratingLabel = new JLabel("the rate of food : " + rate);
        JFrame frame = new JFrame("Food Rating System");
        JButton backButton = new JButton("Back");
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel1 = new JPanel();
        centerPanel1.add(ratingLabel);
        frame.add(centerPanel1 , BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e1 -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showEditCommentForFoodUI(){
        JLabel titleLabel = new JLabel("Edit comment");
        JLabel errorLabel = new JLabel("");
        String commentError = "Type your comment";
        String IDError = "Type your ID comment";
        String IDOwnerError = "You can not edit this comment";
        JButton backButton = new JButton("Back");
        JLabel IDCommentLabel = new JLabel("Enter your comment ID :");
        JTextField IDCommentField = new JTextField(20) ;
        JButton editButton = new JButton("Edit");
        JLabel editCommentLabel = new JLabel("Please type your comment :");
        JTextField editCommentField = new JTextField(20) ;

        JFrame frame = new JFrame("Food Comment System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(10 , 2));
        centerPanel.add(IDCommentLabel);
        centerPanel.add(IDCommentField);
        centerPanel.add(editCommentLabel);
        centerPanel.add(editCommentField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(editButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        editButton.addActionListener(e -> {
            if(editCommentField.getText().equals("")){
                errorLabel.setText(commentError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else if(IDCommentField.getText().equals("")){
                errorLabel.setText(IDError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                boolean bool = MainMenu.handleEditCommentsOfFoodForCustomer(editCommentField.getText() , Integer.parseInt(IDCommentField.getText()));
                if(bool) {
                    JLabel successLabel = new JLabel("Edited successfully");
                    JFrame frame1 = new JFrame("Successful");
                    JButton okButton = new JButton("ok");

                    frame1.setSize(250, 150);
                    frame1.setResizable(false);
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel centerPanel1 = new JPanel();
                    centerPanel1.add(successLabel);
                    frame1.add(centerPanel1, BorderLayout.CENTER);

                    JPanel bottomPanel1 = new JPanel();
                    bottomPanel1.add(okButton);
                    frame1.add(bottomPanel1, BorderLayout.SOUTH);

                    okButton.addActionListener(e1 -> {
                        frame1.setVisible(false);
                        showEditCommentForFoodUI();
                    });
                    frame.setVisible(false);
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                }
                else{
                    errorLabel.setText(IDOwnerError);
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setVisible(true);
                    frame.setVisible(true);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showAddNewCommentForFoodUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Add new comment :");
        JLabel errorLabel = new JLabel("");
        String commentError = "Type your comment";
        JButton addButton = new JButton("Add");
        JLabel addCommentLabel = new JLabel("Add your comment");
        JTextField addCommentField = new JTextField(20) ;
        addCommentField.setSize(40 , 20);

        JFrame frame = new JFrame("Food Comment System");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(10 , 2));
        centerPanel.add(addCommentLabel);
        centerPanel.add(addCommentField);
        centerPanel.add(errorLabel);
        errorLabel.setVisible(false);
        frame.add(centerPanel , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1 , 2));
        bottomPanel.add(backButton);
        bottomPanel.add(addButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        addButton.addActionListener(e -> {
            if(addCommentField.getText().equals("")){
                errorLabel.setText(commentError);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                frame.setVisible(true);
            }
            else {
                MainMenu.handleAddNewCommentForFoodForCustomer(addCommentField.getText());
                JLabel successLabel = new JLabel("Added successfully");
                JFrame frame1 = new JFrame("Successful");
                JButton okButton = new JButton("ok");
                frame1.setSize(250, 150);
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel centerPanel1 = new JPanel();
                centerPanel1.add(successLabel);
                frame1.add(centerPanel1, BorderLayout.CENTER);

                JPanel bottomPanel1 = new JPanel();
                bottomPanel1.add(okButton);
                frame1.add(bottomPanel1, BorderLayout.SOUTH);

                okButton.addActionListener(e1 -> {
                    frame1.setVisible(false);
                    showAddNewCommentForFoodUI();
                });
                frame.setVisible(false);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void showDisplayFoodCommentsUI(){
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("The comments");

        JFrame frame = new JFrame("Food Comment System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        ArrayList<String> showCommentWithResponse = new ArrayList<>();
        MainMenu.handleDisplayCommentsOfFoodForCustomer(showCommentWithResponse);

        String[] array = showCommentWithResponse.toArray(new String[showCommentWithResponse.size()]);

        JList<String> list = new JList<>(array);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.add(backButton);
        frame.add(bottomPanel1, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.setVisible(false);
            showFoodOptionForCustomerUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        showLoginRegisterUI();
    }
}
