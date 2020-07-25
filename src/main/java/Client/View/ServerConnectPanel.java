package Client.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectPanel extends JPanel {

    private final JLabel label;
    private final JButton button;
    private final Font font = new Font("Serif", Font.BOLD, 25);
    private final int port;


    public ServerConnectPanel(int port) {
        this.port = port;
        setLayout(null);
        setSize(800, 800);
        setPreferredSize(new Dimension(800, 800));
        label = new JLabel("Click this button to Connect to the Server");
        label.setFont(font);
        label.setBounds(300, 200, 200, 60);
        add(label);

        button = new JButton("Connect to The Server");
        button.setBounds(300, 400, 200, 60);
        button.setFocusable(false);
        button.addActionListener(e -> {
            Socket socket;
            try {
                System.out.println("client connect to port : " + port);
                socket = new Socket("localhost", port);
                MyFrame.getInstance().createLoginPanel(socket);
            } catch (IOException ex) {
                ex.printStackTrace();
                label.setText("Problem with connection to the server.");
            }

        });
        add(button);
    }
}
