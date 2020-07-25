package Client.View;

import Client.Controller.RequestHandler;
import Client.Model.AuthToken;
import Client.View.Configs.ConfigsLoader;
import Client.View.Configs.LoginConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class LoginPanel extends JPanel implements ActionListener, MouseListener {


    private JButton source;

    private JButton createAccount;
    private JButton enter;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel error;
    private JTextField userField;
    private JTextField passField;
    private LoginConfig config;
    private Socket socket;
    private AuthToken authToken;

    private void initConfig() {
        config = ConfigsLoader.getInstance().getLoginConfig();
    }


    public LoginPanel(Socket socket) {
        this.socket=socket;
        initConfig();
        setLayout(null);
        createAccount = new JButton("Create new Account");
        createAccount.setFont(Constants.f2.deriveFont(20.0f));

        enter = new JButton("Enter");
        enter.setFont(Constants.f2.deriveFont(20.0f));

        userLabel = new JLabel("Username : ");
        userLabel.setFont(Constants.f2);
//        userLabel.setForeground(Color.YELLOW);

        passLabel = new JLabel("Password  : ");
        passLabel.setFont(Constants.f2);
//        passLabel.setForeground(Color.YELLOW);

        error = new JLabel("");
        error.setFont(Constants.f2);
        error.setForeground(Color.RED);

        userField = new JTextField(10);
        passField = new JTextField(10);


        createAccount.setFont(Constants.f2.deriveFont(20.f));
        createAccount.setFocusable(false);
        createAccount.addActionListener(this);
        createAccount.addMouseListener(this);

        enter.setFont(Constants.f2.deriveFont(20.f));
        enter.setFocusable(false);
        enter.addActionListener(this);
        enter.addMouseListener(this);


        userField.setBounds(config.getUserlabelX() + 150, config.getUserLabelY(), config.getUserLabelWidth(), config.getUserLabelHeight());
        passField.setBounds(config.getUserlabelX() + 150, config.getUserLabelY() + 50, config.getUserLabelWidth(), config.getUserLabelHeight());
        userLabel.setBounds(config.getUserlabelX(), config.getUserLabelY(), config.getUserLabelWidth(), config.getUserLabelHeight());
        passLabel.setBounds(config.getUserlabelX(), config.getUserLabelY() + 50, config.getUserLabelWidth(), config.getUserLabelHeight());
        error.setBounds(900, 300, 300, 27);
        enter.setBounds(300, 350, 250, 30);
        createAccount.setBounds(300, 400, 250, 30);


        add(userField);
        add(passField);
        add(userLabel);
        add(passLabel);
        add(enter);
        add(createAccount);
        add(error);
    }

    @Override
    protected void paintComponent(Graphics gd) {
        Graphics2D g = (Graphics2D) gd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            if (userField.getText().equals("") || passField.getText().equals("")) {
                JOptionPane.showMessageDialog(this , "fill the fields.");
                return;
            }
            RequestHandler.getInstance(socket).Login(this , userField.getText() , passField.getText());
        } else if (e.getSource() == createAccount) {
            if (userField.getText().equals("") || passField.getText().equals("")) {
                return;
            }
            RequestHandler.getInstance(socket).SignUp(this,userField.getText() , passField.getText());
//            RequestHandler.SendRequest.SignUp.response(userField.getText(), passField.getText());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        source = (JButton) e.getSource();
        source.setBackground(new Color(240, 255, 97));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        source.setBackground(Color.WHITE);
    }

    public JLabel getError() {
        return error;
    }


    public JTextField getUserField() {
        return userField;
    }


    public JTextField getPassField() {
        return passField;
    }

}
