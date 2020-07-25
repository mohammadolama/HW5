package Client.Controller;

import Client.Model.Message;
import Client.Model.Player;
import Client.Model.PlayerModel;
import Client.View.BoardPanel;
import Client.View.LoginPanel;
import Client.View.MyFrame;
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
    private ObjectMapper objectMapper;
    private final Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private String token;
    private ArrayList<String[][]> replay;


    private RequestHandler(Socket socket) {
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            objectMapper = new ObjectMapper();
            replay = new ArrayList<>();
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
        try {
            String message = objectMapper.writeValueAsString(new Message("login", null));
            printWriter.println(message);
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("ready")) {
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
                }
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Server error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SignUp(LoginPanel loginPanel, String username, String password) {
        try {
            String message = objectMapper.writeValueAsString(new Message("account", null));
            printWriter.println(message);
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("ready")) {
                printWriter.println(username);
                printWriter.println(password);
                String res = scanner.nextLine();
                JOptionPane.showMessageDialog(loginPanel, res);
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Server Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PlayerModel> scoreBoard() {
        ArrayList<PlayerModel> list = new ArrayList<>();
        try {
            String message = objectMapper.writeValueAsString(new Message("scoreboard", token));
            printWriter.println(message);
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("ready")) {
                String result = scanner.nextLine();
                list = objectMapper.readValue(result, new TypeReference<ArrayList<PlayerModel>>() {
                });
            } else {
                JOptionPane.showMessageDialog(MyFrame.getInstance(), resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Player info() {
        Player player = null;
        try {
            String message = objectMapper.writeValueAsString(new Message("info", token));
            printWriter.println(message);
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("ready")) {
                String result = scanner.nextLine();
                player = objectMapper.readValue(result, Player.class);
            } else {
                JOptionPane.showMessageDialog(MyFrame.getInstance(), resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    public void playGame() {
        new Thread(() -> {
            try {
                String message1 = objectMapper.writeValueAsString(new Message("play", token));
                printWriter.println(message1);
                String resp = scanner.nextLine();
                if (resp.equalsIgnoreCase("ready")) {
                    String friendly = scanner.nextLine();
                    String opponent = scanner.nextLine();
                    String opponentName = scanner.nextLine();
                    BoardPanel boardPanel = MyFrame.getInstance().createGamePanel(socket, friendly, opponent, opponentName);
                    replay = new ArrayList<>();
                    while (true) {
                        try {
                            String t = scanner.nextLine();
                            Message message = objectMapper.readValue(t, Message.class);
                            replay.add(message.getValue());
                            boardPanel.processMessage(message);
                            if (message.getMessage().equalsIgnoreCase("you win") ||
                                    message.getMessage().equalsIgnoreCase("you lose") ||
                                    message.getMessage().equalsIgnoreCase("tie")) {
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MyFrame.getInstance(), resp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void occupySpot(String index) {
        try {
            String message = objectMapper.writeValueAsString(new Message("index", token));
            printWriter.println(message);
            printWriter.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[][]> getReplay() {
        return replay;
    }
}