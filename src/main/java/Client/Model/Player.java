package Client.Model;


public class Player {

    private String  token;
    private String username;
    private int numberOfWins;
    private int numberOfLost;
    private int totalPLays;
    private int score;
    private boolean online;

    public Player() {
    }

    public Player(String name,String  token, int numberOfWins, int numberOfLost, int totalPLays, int score) {
        this.username = name;
        this.token = token;
        this.numberOfWins = numberOfWins;
        this.numberOfLost = numberOfLost;
        this.totalPLays = totalPLays;
        this.score = score;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfLost() {
        return numberOfLost;
    }

    public void setNumberOfLost(int numberOfLost) {
        this.numberOfLost = numberOfLost;
    }

    public int getTotalPLays() {
        return totalPLays;
    }

    public void setTotalPLays(int totalPLays) {
        this.totalPLays = totalPLays;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
