package frontend;

import backend.login.User;
import frontend.client.client;
import frontend.client.client_dash;
import frontend.owner.owner;
import frontend.owner.owner_dash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userMain {
    private JFrame mainFrame;
    private JPanel mainPanel, buttonPanel;
    private JButton selectUser, selectOwner;
    private User user;
    public userMain(User user) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.user = user;
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        getMainFrame();
    }
    private void setButtons() {
        selectUser = new JButton("Client Dashboard");
        selectOwner = new JButton("Owner Dashboard");

        Color buttonColor = new Color(100, 150, 250);
        selectUser.setBackground(buttonColor);
        selectUser.setForeground(Color.WHITE);
        selectOwner.setBackground(buttonColor);
        selectOwner.setForeground(Color.WHITE);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        selectUser.setFont(buttonFont);
        selectOwner.setFont(buttonFont);

        selectUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.dispose();
                new client_dash(user);
            }
        });

        selectOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new owner_dash(user);
                mainFrame.dispose();
            }
        });
    }

    private void setButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(selectOwner);
        buttonPanel.add(selectUser);
    }

    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeMessage, userSelectMessage;
        welcomeMessage = new JLabel("Welcome, " + user.getName() + "!");

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        welcomeMessage.setFont(labelFont);

        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(welcomeMessage);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }

    public void setMainFrame() {
        mainFrame = new JFrame("VCRTS Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(true);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
    }
    public void getMainFrame() {
        setButtons();
        setButtonPanel();
        setMainPanel();
        setMainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
