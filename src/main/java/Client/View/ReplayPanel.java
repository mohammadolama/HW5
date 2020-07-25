package Client.View;

import Client.View.Configs.Config;
import Client.View.Configs.ConfigsLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class ReplayPanel extends JPanel {

    private ArrayList<String[][]> replayes;

    private String[][] values = new String[7][7];
    private final JButton[][] buttons = new JButton[7][7];
    private Socket socket;
    private Config config = ConfigsLoader.getInstance().getConfig();
    private JButton next;
    private int i = 0;

    public ReplayPanel(Socket socket, ArrayList<String[][]> replayes) {
        this.socket = socket;
        this.replayes = replayes;
        setLayout(null);
        setSize(config.getFrameWidth(), config.getFrameHeight());
        createButtons();
        initBUttons(0);

        next = new JButton();
        next.setBounds(350, 700, 100, 50);
        if (replayes.size() == 0) {
            next.setText("Finish");
        } else {
            next.setText("next");
        }
        next.setFocusable(false);
        next.addActionListener(al);
        add(next);
    }

    private void initBUttons(int index) {
        if (replayes.size() > 0) {
            String[][] val = replayes.get(index);
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 7; k++) {
                    buttons[j][k].setText(val[j][k]);
                }
            }
        }
    }

    private void createButtons() {
        for (int k = 0; k < 7; k++) {
            for (int j = 0; j < 7; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Serif", Font.BOLD, 25));
                button.setName(k + "" + j);
                button.setBounds(100 + 80 * k, 100 + 80 * j, 80, 80);
                button.setFocusable(false);
                add(button);
                buttons[k][j] = button;
            }
        }
    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == next) {
                if (replayes.size() == 0) {
                    MyFrame.getInstance().createMenuPanel(socket);
                } else if (i == replayes.size() - 1) {
                    MyFrame.getInstance().createMenuPanel(socket);
                } else {
                    i++;
                    if (i == replayes.size() - 2) {
                        next.setText("finish");
                    }
                    initBUttons(i);
                }

            }
        }
    };

}
