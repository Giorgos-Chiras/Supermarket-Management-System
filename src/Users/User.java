package Users;

public class User {
    private final String name;
    private final Position position;
    private final String id;

    public User(String name, Position position, String id) {
        this.name = name;
        this.position = position;
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
