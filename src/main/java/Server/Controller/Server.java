package Server.Controller;

import Server.Model.AuthToken;
import Server.Model.Player;
import Server.Model.PlayerModel;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private int defaultPort = 8001;
    private ArrayList<Socket> sockets;
    private HashMap<AuthToken, ClientHandler> clients;
    private HashMap<String, Player> allPlayers;
    private HashMap<String, Player> onlinePlayers;
    private ArrayList<Triplet> waitingPlayer;
    private Scanner scanner = new Scanner(System.in);
    private HashMap<String, Integer> scores = new HashMap<>();
    SecureRandom secureRandom = new SecureRandom();
    Base64.Encoder base64encoder = Base64.getUrlEncoder();

    public Server() {
        try {
            sockets = new ArrayList<>();
            onlinePlayers = new HashMap<>();
            serverSocket = new ServerSocket(defaultPort);
            clients = new HashMap<>();
            waitingPlayer = new ArrayList<>();
            loadPlayers();

            scores = new HashMap<>();
            scores.put("Ahmad1", 20);
            scores.put("Ahmad2", 18);
            scores.put("Ahmad3", 60);
            scores.put("Ahmad4", 185);
            scores.put("Ahmad5", 96);
            scores.put("Ahmad6", 85);
            scores.put("Ahmad7", 0);
            scores.put("Ahmad8", 345);
            scores.put("Ahmad9", 2001);
            scores.put("Ahmad10", 500);


            System.out.println("Server started at : " + defaultPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public String generateToken() {
        byte[] random = new byte[16];
        secureRandom.nextBytes(random);
        return base64encoder.encodeToString(random);
    }

    public synchronized void loadPlayers() {
        allPlayers = JsonReaders.loadPlayers();
    }

    public void addOnlinePlayer(Player player) {
        onlinePlayers.put(player.getUsername(), player);
    }

    public void putInWaitList(Triplet triplet) {
        synchronized (waitingPlayer) {
            waitingPlayer.add(triplet);
            createGame();
        }


    }

    private void createGame() {
        if (waitingPlayer.size() >= 2) {
            new Thread(() -> {
                waitingPlayer.get(0).getClientHandler().notification("X" , "O" , waitingPlayer.get(1).getPlayer().getUsername());
                waitingPlayer.get(1).getClientHandler().notification("O" , "X" , waitingPlayer.get(0).getPlayer().getUsername());
                GameManager gameManager = new GameManager(this , waitingPlayer.get(0).getPlayer() , waitingPlayer.get(1).getPlayer() ,
                        waitingPlayer.get(0).getClientHandler() , waitingPlayer.get(1).getClientHandler());
                gameManager.start();
                waitingPlayer.get(0).getClientHandler().setGameManager(gameManager);
                waitingPlayer.get(1).getClientHandler().setGameManager(gameManager);

                waitingPlayer.remove(0);
                waitingPlayer.remove(0);
            }).start();
        }
    }

    @Override
    public void run() {
//        new myThread().start();
        while (isInterrupted() == false) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket, null).start();
                System.out.println("new Player Joined the server");
                sockets.add(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }

    public synchronized void UpdatePlayer(Player winner, Player loser) {
        JsonBuilders.PlayerJsonBuilder(winner.getUsername(), winner);
        JsonBuilders.PlayerJsonBuilder(loser.getUsername(), loser);
    }

    public String PlayerStatus() {
        loadPlayers();

        onlinePlayers.keySet().forEach(p -> System.out.println(p));

        ArrayList<PlayerModel> list = new ArrayList<>();


        for (Map.Entry<String, Player> entry : allPlayers.entrySet()) {
            if (onlinePlayers.containsKey(entry.getKey())) {

                list.add(new PlayerModel(entry.getKey(), true, entry.getValue().getScore()));
            } else {
                list.add(new PlayerModel(entry.getKey(), false, entry.getValue().getScore()));
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setOffline(Player player) {
        onlinePlayers.remove(player.getUsername() , player);
    }
}
