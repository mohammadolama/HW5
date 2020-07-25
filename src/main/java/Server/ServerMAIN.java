package Server;

import Server.Controller.Server;

public class ServerMAIN {
    public static void main(String[] args) {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            new Server(port).start();
        } else {
            new Server(8000).start();
        }

    }
}
