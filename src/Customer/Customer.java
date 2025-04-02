package Customer;

import java.util.Objects;

public class Customer {
    private String name;
    private String email;
    private String phone;
    BonusCard bonusCard = null;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public float calculateDiscount() {
        return 0;
    }

    //General functions
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    @Override
    public int hashCode() {
        return phone.hashCode();
    }

    public boolean equals(Customer customer) {
        return customer != null && customer.getPhone().equals(this.getPhone());
    }

    public String toString() {
        return "Name: " + this.getName() + ", Phone: " + this.getPhone() + ", Email: " + this.getEmail();
    }
}
