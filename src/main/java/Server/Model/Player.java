package Server.Model;


import org.codehaus.jackson.annotate.JsonIgnore;

public class Player {

    private String  token;
    private String username;
    private int numberOfWins;
    private int numberOfLost;
    private int totalPLays;
    private int score;
    private boolean online;


    public Player(String username){
        this.username = username;
        numberOfWins = 0 ;
        numberOfLost = 0 ;
        totalPLays = 0 ;
        score = 0 ;
        online = false;
    }

    public Player(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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
}
