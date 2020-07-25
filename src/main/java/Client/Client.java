package Client;

import Client.View.MyFrame;

import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private MyFrame myFrame;

    public static void main(String[] args) {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            MyFrame.getInstance(port);
        } else {
            MyFrame.getInstance(8000);
        }
    }
}
