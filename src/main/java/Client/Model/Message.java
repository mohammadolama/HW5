package Client.Model;

public class Message {

    private boolean yourTurn;
    private String message;
    private String [][] value;

    public Message(boolean yourTurn, String message, String[][] value) {
        this.yourTurn = yourTurn;
        this.message = message;
        this.value = value;
    }

    public Message() {
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[][] getValue() {
        return value;
    }

    public void setValue(String[][] value) {
        this.value = value;
    }
}
