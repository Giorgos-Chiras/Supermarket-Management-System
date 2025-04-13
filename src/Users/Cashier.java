package Users;

/**
 * Cashier class that extends from user
 * Doesn't have any methods as it has no privileges
 */
public class Cashier extends User {
    public Cashier(String name,String id) {
        super(name, UserPosition.CASHIER,id);
    }

}
