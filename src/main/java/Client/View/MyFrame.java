package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.Player;
import Client.Model.PlayerModel;
import Client.View.Configs.Config;
import Client.View.Configs.ConfigsLoader;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MyFrame extends JFrame {

    private static MyFrame myFrame;
    private final int id;
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private final Config config = ConfigsLoader.getInstance().getConfig();

    public MyFrame(int port) {
        Random random = new Random();
        id = random.nextInt();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(config.getFrameWidth(), config.getFrameHeight());
        ServerConnectPanel panel1 = new ServerConnectPanel(port);
        setContentPane(panel1);
        setVisible(true);
    }

    public static MyFrame getInstance(int port) {
        if (myFrame == null) {
            myFrame = new MyFrame(port);
        }
        return myFrame;
    }

    public static MyFrame getInstance() {
        return myFrame;
    }


    public void createLoginPanel(Socket socket) {
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginPanel loginPanel = new LoginPanel(socket);
        setContentPane(loginPanel);
        revalidate();
        repaint();
    }

    public void createMenuPanel(Socket socket) {
        MainMenu mainMenu = new MainMenu(socket);
        setContentPane(mainMenu);
        revalidate();
        repaint();

    }

    public void createInfoPanel(Socket socket) {
        Player player = RequestHandler.getInstance(socket).info();
        InfoPanel infoPanel = new InfoPanel(socket, player);
        setContentPane(infoPanel);
        revalidate();
        repaint();
    }

    public void createScoreBoardPanel(Socket socket) {
        ArrayList<PlayerModel> playerModels = RequestHandler.getInstance(socket).scoreBoard();
        ScorePanel scorePanel = new ScorePanel(playerModels, socket);
        setContentPane(scorePanel);
        revalidate();
        repaint();
    }

    public void createReplayPanel(Socket socket) {
        ArrayList<String[][]> replays = RequestHandler.getInstance(null).getReplay();
        ReplayPanel replayPanel = new ReplayPanel(socket, replays);
        setContentPane(replayPanel);
        revalidate();
        repaint();
    }

    public BoardPanel createGamePanel(Socket socket, String friendly, String opponent, String opponentName) {
        BoardPanel boardPanel = new BoardPanel(socket, friendly, opponent, opponentName);
        setContentPane(boardPanel);
        revalidate();
        repaint();
        return boardPanel;
    }

    public static void main(String[] args) {
        MyFrame myFrame = MyFrame.getInstance();
    }

}