package Client.View;

import Client.Controller.Request;
import Client.Controller.RequestHandler;
import Client.Model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class BoardPanel extends JPanel {

    private Socket socket;

    private String friendly;
    private String opponent;
    private String opponentName;
    private String ServerMessage = "Wait for opponent";
    private final Object object = new Object();

    private boolean ourTurn;
    private String [][]values = new String[7][7];
    private JButton[][] buttons = new JButton[7][7];
    public BoardPanel(Socket socket , String friendly , String opponent , String opponentName){
        this.socket = socket;
        this.friendly = friendly;
        this . opponent = opponent;
        this.opponentName = opponentName;
        ourTurn = friendly.equals("X");
        setLayout(null);
        setSize(800,800);
        setPreferredSize(new Dimension(800,800));
        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Serif" , Font.BOLD , 25));
//                button.setText(i+""+j);
                button.addActionListener(al);
                button.setName(i+""+j);
                button.setBounds(100+80*i,100+80*j,80,80);
                button.setFocusable(false);
                add(button);
                buttons[i][j] = button;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        ((Graphics2D) gr).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gr.setFont(new Font("Serif" , Font.BOLD , 30));
        g.setColor(Color.white);
        gr.fillRect(0,0,800,800);
        g.setColor(Color.BLACK);
        g.drawString(friendly , 50 , 50);

        g.drawString("Opponent : " + opponentName , 450 , 50);
        FontMetrics fontMetrics=gr.getFontMetrics();
        int width = fontMetrics.stringWidth(ServerMessage);
        g.drawString(ServerMessage , (800-width)/2 , 720);
    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();
            if (src.getText()==null || src.getText().equals("")){
                if (ourTurn) {
                    src.setText(friendly);
                    RequestHandler.getInstance(null).occupySpot(BoardPanel.this , src.getName());
                    src.removeActionListener(al);
                } else {
                    JOptionPane.showMessageDialog(BoardPanel.this, "It's not your Turn.");
                }
            }else {
                JOptionPane.showMessageDialog(BoardPanel.this , "Spot is occupied");
                src.removeActionListener(al);
            }
        }
    };

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    });

//    public void getMessageFromServer(String message){
//        if (message.equals("Your Turn")){
//            ourTurn = true;
//            ServerMessage = message;
//        }else if (message.equals("Opponent Turn")){
//            ourTurn = false;
//            ServerMessage = message;
//        }else {
//            JOptionPane.showMessageDialog(BoardPanel.this , message);
//        }
//    }


    public void processMessage(Message message) {
        values = message.getValue();
        updateButtons();
        if (message.isYourTurn()){
            ourTurn=true;
        }else {
            ourTurn = false;
        }
        if (message.getMessage().equalsIgnoreCase("you win") || message.getMessage().equalsIgnoreCase("you lose") || message.getMessage().equalsIgnoreCase("tie")){
            int i = JOptionPane.showConfirmDialog(this , message.getMessage() ,"Game finished" , JOptionPane.DEFAULT_OPTION );
            if (i == 0){
                MyFrame.getInstance().createMenuPanel(socket);
            }
        }
        ServerMessage = message.getMessage();
    }

    private void updateButtons() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (values[i][j] != null){
                    buttons[i][j].setText(values[i][j]);
                }
            }
        }
        revalidate();
        repaint();
    }
}
