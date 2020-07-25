package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.Player;
import Client.Model.PlayerModel;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class ScorePanel extends JPanel implements ActionListener {

    private ArrayList<PlayerModel> list;
    private String scoreBoard = "ScoreBoard";
    private String emptyScoreBoard = "ScoreBoard is empty .";
    private JButton back;
    private JScrollPane scrollPane;
    private JTable jTable;
    private Timer timer;
    Font font1 = new Font("Serif", Font.BOLD, 50);

    private Socket socket;
    private Player player;

    public ScorePanel(ArrayList<PlayerModel> list , Socket socket) {
        this.list=list;
        this.socket = socket;
        timer=new Timer(5000 , al);
        timer.start();

        setLayout(null);
        setSize(800, 800);
        setPreferredSize(new Dimension(800, 800));

        initTable();

        back=new JButton("back");
        back.setBounds(680,700,80,30);
        back.setFocusable(false);
        back.addActionListener(this);
        add(back);


    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics = g.getFontMetrics(font1);
        int width = fontMetrics.stringWidth(scoreBoard);
        g.setFont(font1);
        g.drawString(scoreBoard, (800 - width) / 2, 100);
        if (list != null){
        }
    }


    private void sortList(ArrayList<PlayerModel> list){
        list.sort(Comparator.comparing(PlayerModel::getScore).thenComparing(PlayerModel::getUsername));
        this.list = list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            MyFrame.getInstance().createMenuPanel(socket);
        }
    }

    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            initTable();
            revalidate();
        }
    };


    private void initTable(){
        this.list = RequestHandler.getInstance(null).scoreBoard();
        sortList(this.list);
        String col[] = {"UserName" , "Online Status" , "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(col , 0);

        for (int i = list.size()-1 ; i >=0 ; i--) {
            Object[] obj = {"  "+list.get(i).getUsername() , list.get(i).isOnline()?"  Online  " : "  Offline  " ,"  "+ list.get(i).getScore()};
            tableModel.addRow(obj);
        }

        jTable = new JTable(tableModel);
        jTable.setFont(new Font("Serif", Font.BOLD, 25));
        jTable.setRowHeight(40);
        jTable.setFocusable(false);
        jTable.setPreferredSize(new Dimension(500, 500));
        scrollPane = new JScrollPane(jTable);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        scrollPane.setBounds(200,200,500,500);
        add(scrollPane);
    }
}
