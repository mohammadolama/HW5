package Server.Controller;

import Server.Model.Message;
import Server.Model.Player;
import Server.Model.Triplet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private final Server server;
    private GameManager gameManager;
    private final Socket socket;
    private Player player;
    private PrintWriter output;
    private Scanner input;
    private final LoginLogic loginLogic = LoginLogic.getInstance();
    private final ObjectMapper objectMapper;


    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            while (socket.isConnected()) {
                Message message = getMessageOf(input.nextLine());
                switch (message.getMessage()) {
                    case "scoreboard":
                        ScoreBoard(message);
                        break;
                    case "login":
                        Login(message);
                        break;
                    case "account":
                        SignUp(message);
                        break;
                    case "info":
                        Info(message);
                        break;
                    case "play":
                        putInList(message);
                        break;
                    case "index":
                        occupyIndex(message);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.setOffline(player);
        }
    }

    private void occupyIndex(Message message) {
        String index = input.nextLine();
        int i = Integer.parseInt(index) / 10;
        int j = Integer.parseInt(index) % 10;
        gameManager.OccupySpot(this.player, i, j);
    }

    private void Info(Message message) {
        if (server.validateToken(message.getToken())) {
            output.println("ready");
            try {
                String res = objectMapper.writeValueAsString(this.player);
                output.println(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            output.println("invalid token");
        }
    }

    private void putInList(Message message) {
        if (server.validateToken(message.getToken())) {
            output.println("ready");
            server.putInWaitList(new Triplet(player, input, output, this));
        } else {
            output.println("invalid token");
        }
    }

    private void Login(Message message) {
        output.println("ready");
        String username = input.nextLine();
        String password = input.nextLine();
        String res = loginLogic.Login(username, password);
        if (res.equals("ok")) {
            String token = AuthTokenGenerator.generateToken();
            Player player = JsonReaders.PlayerJsonReader(username);
            player.setOnline(true);
            player.setToken(token);
            server.addOnlinePlayer(player);
            this.player = player;
            output.println(token);
        } else {
            output.println(res);
        }
    }

    private void SignUp(Message message) {
        output.println("ready");
        String username = input.nextLine();
        String password = input.nextLine();
        String res = loginLogic.SignUp(username, password);
        output.println(res);
    }

    private void ScoreBoard(Message message) {
        if (server.validateToken(message.getToken())) {
            output.println("ready");
            String result = server.PlayerStatus();
            output.println(result);
        } else {
            output.println("invalid token");
        }
    }


    public void notifyClient(Message message) {
        try {
            String res = objectMapper.writeValueAsString(message);
            output.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void notification(String friendly, String opponent, String opponentName) {
        output.println(friendly);
        output.println(opponent);
        output.println(opponentName);
    }

    public Message getMessageOf(String string) {
        Message message = null;
        try {
            message = objectMapper.readValue(string, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
