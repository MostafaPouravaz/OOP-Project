package models;

import java.io.IOException;

public class Vendor extends User{

    public Vendor(String username, String password, String animalName) throws IOException {
        super(username, password, animalName);
    }

}
