package Users;

public class User {
    private final String name;
    private final UserPosition position;
    private final String id;

    public User(String name, UserPosition position, String id) {
        this.name = name;
        this.position = position;
        this.id = id;
    }

    public UserPosition getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public String toString() {
        return "Name: " + name + " Position: " + position + " ID: " + id;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return name.equals(user.name) || id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
