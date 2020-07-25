package Server.Controller;

import Server.Model.Player;
import Server.Model.PlayerModel;
import Server.Model.Triplet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ArrayList<String> validTokens;
    private HashMap<String, Player> allPlayers;
    private HashMap<String, Player> onlinePlayers;
    private ArrayList<Triplet> waitingPlayer;
    private ObjectMapper objectMapper;
    private int port;

    public Server(int port) {
        try {
            this.port = port;
            onlinePlayers = new HashMap<>();
            serverSocket = new ServerSocket(port);
            validTokens = new ArrayList<>();
            waitingPlayer = new ArrayList<>();
            objectMapper = new ObjectMapper();
            loadPlayers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("server started at port : " + port);
//        new myThread().start();
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void loadPlayers() {
        allPlayers = JsonReaders.loadPlayers();
    }

    public void addOnlinePlayer(Player player) {
        onlinePlayers.put(player.getUsername(), player);
        validTokens.add(player.getToken());
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

    public synchronized void UpdatePlayer(Player winner, Player loser) {
        JsonBuilders.PlayerJsonBuilder(winner.getUsername(), winner);
        JsonBuilders.PlayerJsonBuilder(loser.getUsername(), loser);
    }

    public String PlayerStatus() {
        loadPlayers();
        ArrayList<PlayerModel> list = new ArrayList<>();
        for (Map.Entry<String, Player> entry : allPlayers.entrySet()) {
            if (onlinePlayers.containsKey(entry.getKey())) {
                list.add(new PlayerModel(entry.getKey(), true, entry.getValue().getScore()));
            } else {
                list.add(new PlayerModel(entry.getKey(), false, entry.getValue().getScore()));
            }
        }
        String result = null;
        try {
            result = objectMapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setOffline(Player player) {
        validTokens.remove(player.getToken());
        onlinePlayers.remove(player.getUsername(), player);
    }

    public boolean validateToken(String token) {
        return validTokens.contains(token);
    }
}
