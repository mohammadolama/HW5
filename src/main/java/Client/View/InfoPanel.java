package Client.View;

import Client.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class InfoPanel extends JPanel implements ActionListener {

    private Player player;
    private Socket socket;
    private JButton back;

    public InfoPanel(Socket socket  , Player player){
        this.socket=socket;
        this.player = player;

        setLayout(null);

        back=new JButton("back");
        back.setBounds(680,700,80,30);
        back.setFocusable(false);
        back.addActionListener(this);
        add(back);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = ( Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Serif" , Font.BOLD , 30));
        g.drawString("Username               : " + player.getUsername() , 100 , 100 );
        g.drawString("Total Playes           : " + player.getTotalPLays() , 100 , 200);
        g.drawString("Total Wins             : " + player.getNumberOfWins() , 100 , 300);
        g.drawString("Total lost             : " + player.getNumberOfLost() , 100 , 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            MyFrame.getInstance().createMenuPanel(socket);
        }
    }
}
