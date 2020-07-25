package Server.Controller;

import Server.Model.Message;
import Server.Model.Player;


public class GameManager extends Thread {

    private final Server server;

    private final Player player1;
    private final Player player2;

    private Player currentPlayer;

    private Player winner;

    private final Object object = new Object();

    private final ClientHandler clientHandler1;
    private final ClientHandler clientHandler2;

    private final String[][] values = new String[7][7];

    private boolean isFinished = false;

    public GameManager(Server server, Player player1, Player player2, ClientHandler clientHandler1, ClientHandler clientHandler2) {
        this.server = server;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
    }

    @Override
    public void run() {
        Message message1 = new Message(true, "You go first", values);
        clientHandler1.notifyClient(message1);
        Message message2 = new Message(false, "Wait for opponent move ...", values);
        clientHandler2.notifyClient(message2);

        while (!isFinished) {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (winner == player1) {
            clientHandler1.notifyClient(new Message(false, "You win", values));
            clientHandler2.notifyClient(new Message(false, "You lose", values));
            updatePlayersState(player1 , player2);
        } else if (winner == player2) {
            clientHandler1.notifyClient(new Message(false, "You lose", values));
            clientHandler2.notifyClient(new Message(false, "You win", values));
            updatePlayersState(player2 , player1);
        } else {
            clientHandler1.notifyClient(new Message(false, "Tie", values));
            clientHandler2.notifyClient(new Message(false, "Tie", values));
            updatePlayerState2(player1 , player2);
        }
    }

    private void checkForWinner() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] != null && values[i][j].equals(values[i][j + 1]) &&
                        values[i][j].equals(values[i][j + 2]) && values[i][j].equals(values[i][j + 3])) {
                    winner = (values[i][j].equals("X") ? player1 : player2);
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if (values[i][j] != null && values[i][j].equals(values[i + 1][j]) &&
                        values[i][j].equals(values[i + 2][j]) && values[i][j].equals(values[i + 3][j])) {
                    winner = (values[i][j].equals("X") ? player1 : player2);
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] != null && values[i][j].equals(values[i + 1][j + 1]) &&
                        values[i][j].equals(values[i + 2][j + 2]) && values[i][j].equals(values[i + 3][j + 3])) {
                    winner = (values[i][j].equals("X") ? player1 : player2);
                }
            }
        }
        for (int i = 3; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] != null && values[i][j].equals(values[i - 1][j + 1]) &&
                        values[i][j].equals(values[i - 2][j + 2]) && values[i][j].equals(values[i - 3][j + 3])) {
                    winner = (values[i][j].equals("X") ? player1 : player2);
                }
            }
        }
        if (winner != null) {
            isFinished = true;
        } else {
            boolean flag = false;
            outer:
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (values[i][j] == null || values[i][j].equals("")) {
                        flag = true;
                        break outer;
                    }

                }
            }
            if (!flag) {
                isFinished = true;
            }
        }
    }

    private void updatePlayersState(Player winner, Player loser) {
        winner.setTotalPLays(winner.getTotalPLays() + 1);
        loser.setTotalPLays(loser.getTotalPLays() + 1);
        winner.setNumberOfWins(winner.getNumberOfWins() + 1);
        loser.setNumberOfLost(loser.getNumberOfLost() + 1);
        winner.setScore(winner.getNumberOfWins() - winner.getNumberOfLost());
        loser.setScore(loser.getNumberOfWins() - loser.getNumberOfLost());
        server.UpdatePlayer(winner, loser);
    }

    private void updatePlayerState2(Player player1 , Player player2){
        player1.setTotalPLays(player1.getTotalPLays() + 1);
        player2.setTotalPLays(player2.getTotalPLays() + 1);
        server.UpdatePlayer(player1 , player2);
    }


    public String OccupySpot(Player player, int i, int j) {
        if (player == player1) {
            values[i][j] = "X";
            Message message1 = new Message(false, "Wait for opponent move ... ", values);
            clientHandler1.notifyClient(message1);
            Message message2 = new Message(true, "Your Turn...", values);
            clientHandler2.notifyClient(message2);
        } else {
            values[i][j] = "O";
            Message message1 = new Message(true, "Your Turn ...", values);
            clientHandler1.notifyClient(message1);
            Message message2 = new Message(false, "Wait for opponent move ...", values);
            clientHandler2.notifyClient(message2);
        }
        checkForWinner();
        synchronized (object) {
            object.notify();
        }
        changeTurn();
        return "Successful";
    }

    private void changeTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

}
