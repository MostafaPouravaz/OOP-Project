package models;

public class Customer extends User {

    public Customer(String username, String password, String animalName) {
        super(username, password, animalName);
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
