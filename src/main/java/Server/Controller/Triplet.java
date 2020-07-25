package Server.Controller;

import Server.Model.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class Triplet {

    private Player player;
    private Scanner scanner;
    private PrintWriter printwriter;
    private ClientHandler clientHandler;

    public Triplet(Player player, Scanner scanner, PrintWriter printwriter, ClientHandler clientHandler) {
        this.player = player;
        this.scanner = scanner;
        this.printwriter = printwriter;
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public PrintWriter getPrintwriter() {
        return printwriter;
    }

    public void setPrintwriter(PrintWriter printwriter) {
        this.printwriter = printwriter;
    }
}
