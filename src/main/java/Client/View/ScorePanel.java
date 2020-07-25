package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.PlayerModel;
import Client.View.Configs.Config;
import Client.View.Configs.ConfigsLoader;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;

public class ScorePanel extends JPanel implements ActionListener {

    private ArrayList<PlayerModel> list;
    private final JButton back;
    Font font1 = new Font("Serif", Font.BOLD, 50);

    private final Socket socket;
    private final Config config = ConfigsLoader.getInstance().getConfig();

    public ScorePanel(ArrayList<PlayerModel> list, Socket socket) {
        this.list = list;
        this.socket = socket;
        Timer timer = new Timer(5000, al);
        timer.start();

        setLayout(null);
        setSize(config.getFrameWidth(), config.getFrameHeight());
        setPreferredSize(new Dimension(config.getFrameWidth(), config.getFrameHeight()));

        initTable();

        back = new JButton("back");
        back.setBounds(config.getBackX(), config.getBackY(), config.getButtonWidth(), config.getButtonHeight());
        back.setFocusable(false);
        back.addActionListener(this);
        add(back);


    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics = g.getFontMetrics(font1);
        String scoreBoard = "ScoreBoard";
        int width = fontMetrics.stringWidth(scoreBoard);
        g.setFont(font1);
        g.drawString(scoreBoard, (-width) / 2, 100);
    }


    private void sortList(ArrayList<PlayerModel> list) {
        list.sort(Comparator.comparing(PlayerModel::getScore).thenComparing(PlayerModel::getUsername));
        this.list = list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            MyFrame.getInstance().createMenuPanel(socket);
        }
    }

    private final ActionListener al = e -> {
        initTable();
        revalidate();
    };


    private void initTable() {
        this.list = RequestHandler.getInstance(null).scoreBoard();
        sortList(this.list);
        String[] col = {"UserName", "Online Status", "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (int i = list.size() - 1; i >= 0; i--) {
            Object[] obj = {"  " + list.get(i).getUsername(), list.get(i).isOnline() ? "  Online  " : "  Offline  ", "  " + list.get(i).getScore()};
            tableModel.addRow(obj);
        }
        JTable jTable = new JTable(tableModel);
        jTable.setFont(new Font("Serif", Font.BOLD, 25));
        jTable.setRowHeight(40);
        jTable.setFocusable(false);
        jTable.setPreferredSize(new Dimension(config.getTableSize(), config.getTableSize()));
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(config.getTableSize(), config.getTableSize()));
        scrollPane.setBounds(config.getTableX(), config.getTableY(), config.getTableSize(), config.getTableSize());
        add(scrollPane);
    }
}
