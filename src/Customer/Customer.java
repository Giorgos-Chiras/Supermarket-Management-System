package Customer;

public class Customer {
    private final String name;
    private final String email;
    private final String phone;
    BonusCard bonusCard;

    /**
        Constructors for customer based on if it's a new customer or existing (in file)
     */
    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        bonusCard = new BonusCard(0,0);
    }

    public Customer(String name, String email, String phone,float totalSpend, int points) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        bonusCard = new BonusCard(points, totalSpend);
    }


    /**
        General functions
     */
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

    public BonusCard getBonusCard() {
        return bonusCard;
    }
    @Override
    public int hashCode() {
        return phone.hashCode();
    }

    public boolean equals(Customer customer) {
        return customer != null && customer.getPhone().equals(this.getPhone());
    }

    public String toString() {
        return "Name: " + this.getName() + " Phone: " + this.getPhone() + " Email: " + this.getEmail();
    }
}
