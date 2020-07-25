package Client.Model;

public class AuthToken {

    private String name;
    private long token;

    public AuthToken() {
    }

    public AuthToken(String name, long token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "name='" + name + '\'' +
                ", token=" + token +
                '}';
    }
}
