package Client.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectPanel extends JPanel {

    private JLabel label;
    private JButton button;
    private Font font=new Font("Serif",Font.BOLD ,25 );

    public ServerConnectPanel(){
        setLayout(null);
        setSize(800,800);
        setPreferredSize(new Dimension(800,800));
        label = new JLabel("Connection Lost");
        label.setFont(font);
        label.setBounds(300 , 200 , 200 , 60);
        add(label);

        button=new JButton("Connect to The Server");
        button.setBounds(300,400,200,60);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket socket=null;
                do {
                    try {
                        socket=new Socket("localhost" , 8001);
                        System.out.println(socket);
                        MyFrame.getInstance().createLoginPanel(socket);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }while (socket==null);

//                MyFrame.getInstance().changePanel("login");
            }
        });
        add(button);
    }
}
