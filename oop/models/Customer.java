package models;

import java.io.IOException;

public class Customer extends User {
    private int charge ;
    public Customer(String username, String password, String animalName) throws IOException {
        super(username, password, animalName);
        this.charge = 0;
    }

    public int getCharge() {
        return this.charge;
    }

    public void setCharge(int charge) {
        this.charge += charge;
    }

    @Override
    public String toString() {
        return this.getUsername();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Customer customer)) {
            return false;
        }

        return this.getUserId() == customer.getUserId();
    }


}