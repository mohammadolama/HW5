package Client;

import Client.Model.AuthToken;
import Client.View.MyFrame;

import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private AuthToken authToken;
    private MyFrame myFrame;

    public static void main(String[] args) {
    }
}
