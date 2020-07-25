package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.AuthToken;
import Client.Model.Player;
import Client.Model.PlayerModel;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class MyFrame extends JFrame {

    private static MyFrame myFrame=new MyFrame();
    private int width = 800;
    private int id;
    private int height = 800;
    private boolean myTurn = true;
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private JPanel panel;
    private CardLayout cardLayout;
    private ScorePanel scorePanel;

    public MyFrame(){
        Random random = new Random();
        id = random.nextInt();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width , height);
        ServerConnectPanel panel1=new ServerConnectPanel();
//        panel=new JPanel();
//        cardLayout=new CardLayout();
//        panel.setLayout(cardLayout);
//        panel.setPreferredSize(new Dimension(800,800));
//        panel.setSize(800,800);
//        panel.add("serverconnecttion" , panel1 );
//        add(panel1);
        setContentPane(panel1);

//        BoardPanel boardPanel= new BoardPanel();
//        add(boardPanel);

        setVisible(true);
    }

    public static MyFrame getInstance(){
        return myFrame;
    }

    public void changePanel(String name){
        cardLayout.show(panel , name);
        revalidate();
        repaint();
    }

    public void createPanels(Socket socket , AuthToken authToken){

    }

    public void createLoginPanel(Socket socket){
        this.socket=socket;
        try {
            printWriter= new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginPanel loginPanel=new LoginPanel(socket);
        System.out.println(1);
        setContentPane(loginPanel);
        System.out.println(2);
        revalidate();
        repaint();
    }

    public void createMenuPanel (Socket socket) {
        MainMenu mainMenu = new MainMenu(socket);
        setContentPane(mainMenu);
        revalidate();
        repaint();

    }

    public void createInfoPanel(Socket socket ){
        Player player = RequestHandler.getInstance(socket).info();
        System.out.println(player.toString());
        InfoPanel infoPanel = new InfoPanel(socket , player);
        setContentPane(infoPanel);
        revalidate();
        repaint();
    }

    @Override
    public String toString() {
        return "MyFrame{" +
                "id=" + id +
                '}';
    }

    public void createScoreBoardPanel(Socket socket){
        System.out.println(socket);
        System.out.println(socket.isConnected());
        ArrayList<PlayerModel> playerModels = RequestHandler.getInstance(socket).scoreBoard();
        System.out.println(playerModels.toString());
        ScorePanel scorePanel = new ScorePanel(playerModels , socket);
        setContentPane(scorePanel);
        revalidate();
        repaint();


    }

    public BoardPanel createGamePanel(Socket socket , String friendly , String opponent , String opponentName){
        BoardPanel boardPanel = new BoardPanel(socket , friendly , opponent , opponentName);
        setContentPane(boardPanel);
        revalidate();
        repaint();
        return boardPanel;
    }

    public static void main(String[] args) throws IOException {
//        HashMap<String ,String > accounts = new HashMap<>();
//        ObjectMapper objectMapper= new ObjectMapper();
//        File file;
//        FileWriter fileWriter=new FileWriter(new File("resources/Data/accounts.json"));
//        objectMapper.writeValue(fileWriter , accounts);
        MyFrame myFrame=MyFrame.getInstance();
//        System.out.println(myFrame);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(i+j + "\t");
            }
            System.out.println();
        }

    }

}