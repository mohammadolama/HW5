package Client.Controller;

import Client.Model.AuthToken;
import Client.Model.Player;

import java.net.Socket;

public class GameState {

    private Socket socket;
    private AuthToken authToken;
    private Player player;




    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
