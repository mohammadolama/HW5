package Client.Model;

public class PlayerModel {

    private String username;
    private boolean online;
    private int score;

    public PlayerModel(String username, boolean online, int score) {
        this.username = username;
        this.online = online;
        this.score = score;
    }

    public PlayerModel(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
