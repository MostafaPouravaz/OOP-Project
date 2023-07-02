package models;

public class Customer extends User {
    private int charge;
    public Customer(String username, String password) {
        super(username, password);
        this.charge = 0;
    }

    public int getCharge() {
        return this.charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
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

        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer customer = (Customer) obj;

        return this.getUserId() == customer.getUserId();
    }

}