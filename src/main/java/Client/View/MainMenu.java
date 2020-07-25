package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MainMenu extends JPanel implements ActionListener {

    private Socket socket;
    private JButton play;
    private JButton info;
    private JButton scoreBoard;



    public MainMenu(Socket socket ){
        this.socket=socket;
        System.out.println("menu  " + socket );
        setLayout(null);

        play=new JButton("Play");
        play.setFont(Constants.f2.deriveFont(23.0f));
        play.setBounds(300,200 , 200,50);
        play.setFocusable(false);
        play.addActionListener(this);
        add(play);


        info=new JButton("Info");
        info.setFont(Constants.f2.deriveFont(23.0f));
        info.setBounds(300,300 , 200,50);
        info.setFocusable(false);
        info.addActionListener(this);
        add(info);

        scoreBoard=new JButton("ScoreBoard");
        scoreBoard.setFont(Constants.f2.deriveFont(23.0f));
        scoreBoard.setBounds(300,400 , 200,50);
        scoreBoard.setFocusable(false);
        scoreBoard.addActionListener(this);
        add(scoreBoard);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play){
            RequestHandler.getInstance(null).playGame();
        }else if (e.getSource() == scoreBoard){
            System.out.println(socket.isConnected());
            MyFrame.getInstance().createScoreBoardPanel(socket);
        }else if (e.getSource() == info){
            MyFrame.getInstance().createInfoPanel(socket);
        }
    }
}
