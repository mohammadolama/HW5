package Server.Controller;

import Server.Model.AuthToken;
import Server.Model.Message;
import Server.Model.Player;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private Server server;
    private GameManager gameManager;
    private Socket socket;
    private AuthToken token;
    private Player player;
    private PrintWriter output;
    private Scanner input;
    private LoginLogic loginLogic = LoginLogic.getInstance();


    public ClientHandler(Server server, Socket socket, AuthToken token) {
        this.server = server;
        this.socket = socket;
        this.token = token;
    }

    @Override
    public void run() {
        System.out.println("the game has been started from the server ...... ");
        try {
            System.out.println("here");
            input = new Scanner(socket.getInputStream());
            System.out.println("input created ... ");
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("output created ....");
            while (socket.isConnected()) {
                System.out.println("Wait for req ...");
                String request = input.nextLine();
                System.out.println("Req recieved : " + request);
                switch (request) {
                    case "scoreboard":
                        ScoreBoard();
                        break;
                    case "login":
                        Login();
                        break;
                    case "account":
                        SignUp();
                        break;
                    case "info":
                        Info();
                        break;
                    case "play":
                        putInList();
                        break;
                    case "index":
                        occupyIndex();
                        break;
                }
                System.out.println("end of req ...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.err.println("socket is close ...");
            server.setOffline(player);
        }
    }

    private void occupyIndex() {
        String index = input.nextLine();
        int i = Integer.parseInt(index)/10;
        int j = Integer.parseInt(index) %10;
        System.out.println(gameManager);
        String result = gameManager.OccupySpot(this.player , i , j);
//        output.println(result);
    }

    private void Info() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String res = objectMapper.writeValueAsString(this.player);
            System.out.println(res);
            output.println(res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void putInList() {
        server.putInWaitList(new Triplet(player , input , output , this ));
    }

    private void Login() {
        output.println("ready");
        String username = input.nextLine();
        String password = input.nextLine();
        String res = loginLogic.Login(username, password);
        if (res.equals("ok")) {
            String token = server.generateToken();
            Player player = JsonReaders.PlayerJsonReader(username);
            player.setOnline(true);
            player.setToken(token);
            server.addOnlinePlayer(player);
            this.player = player;
            output.println(token);
            System.out.println(token);
        } else {
            output.println(res);
        }
    }

    private void SignUp() {
        output.println("ready");
        String username = input.nextLine();
        String password = input.nextLine();
        String res = loginLogic.SignUp(username, password);
        output.println(res);
    }

    private void ScoreBoard() {
        String result = server.PlayerStatus();
        output.println(result);
    }


    public void notifyClient(Message message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String res = objectMapper.writeValueAsString(message);
            output.println(res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void notification(String friendly ,String opponent , String opponentName){
        output.println(friendly);
        output.println(opponent);
        output.println(opponentName);
    }
}
