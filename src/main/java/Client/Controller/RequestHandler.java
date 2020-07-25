package Client.Controller;

import Client.Model.AuthToken;
import Client.Model.Message;
import Client.Model.Player;
import Client.Model.PlayerModel;
import Client.View.BoardPanel;
import Client.View.LoginPanel;
import Client.View.MyFrame;
import Server.Controller.Requests;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestHandler {

    private static RequestHandler requestHandler;

    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private String token;

    private RequestHandler(Socket socket) {
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RequestHandler getInstance(Socket socket) {
        if (requestHandler == null) {
            requestHandler = new RequestHandler(socket);
        }
        return requestHandler;
    }

    public void Login(LoginPanel loginPanel, String username, String password) {
        printWriter.println("login");
        if (scanner.nextLine().equalsIgnoreCase("ready")) {
            printWriter.println(username);
            printWriter.println(password);
            String res = scanner.nextLine();
            if (res.equals("wrong password")) {
                JOptionPane.showMessageDialog(loginPanel, "wrong password .");
            } else if (res.equals("user not found")) {
                JOptionPane.showMessageDialog(loginPanel, "user not found");
            } else {
                token = res;
                MyFrame.getInstance().createMenuPanel(socket);
                System.out.println("login complete");
            }
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Server Error.");
        }
    }

    public void SignUp(LoginPanel loginPanel, String username, String password) {
        printWriter.println("account");
        if (scanner.nextLine().equalsIgnoreCase("ready")) {
            printWriter.println(username);
            printWriter.println(password);
            String res = scanner.nextLine();
            JOptionPane.showMessageDialog(loginPanel, res);
        }
    }

    public ArrayList<PlayerModel> scoreBoard() {
        printWriter.println("scoreboard");
        String result = scanner.nextLine();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<PlayerModel> list = new ArrayList<>() ;
        try {
             list =objectMapper.readValue(result , new TypeReference<ArrayList<PlayerModel>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Player info(){
        Player player = null;
        try {
            printWriter.println("info");
            String result = scanner.nextLine();
            ObjectMapper objectMapper = new ObjectMapper();
            player = objectMapper.readValue(result, Player.class);

        }catch (IOException e){
            e.printStackTrace();
        }
        return player;
    }

    public void playGame(){
        new Thread(() -> {
            printWriter.println("play");
            String friendly = scanner.nextLine();
            String opponent = scanner.nextLine();
            String opponentName = scanner.nextLine();
            BoardPanel boardPanel = MyFrame.getInstance().createGamePanel(socket , friendly , opponent ,opponentName);
            while (true){
                try {
                    String t = scanner.nextLine();
                    System.out.println(t);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Message message = objectMapper.readValue(t, Message.class);
                    boardPanel.processMessage(message);
                    if (message.getMessage().equalsIgnoreCase("you win") ||
                            message.getMessage().equalsIgnoreCase("you lose") ||
                            message.getMessage().equalsIgnoreCase("tie")){
                        break;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void occupySpot(BoardPanel boardPanel , String index){
        printWriter.println("index");
        printWriter.println(index);
//        String res = scanner.nextLine();
//        boardPanel.getMessageFromServer(res);
    }


    public String waitForOpponent(){
        String res = scanner.nextLine();
        if (res.equals("Your Turn")) {
        }

        return res;
    }
}