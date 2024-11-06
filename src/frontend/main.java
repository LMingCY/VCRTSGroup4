package frontend;

import frontend.vc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    private static JFrame mainFrame;
    private static JPanel mainPanel, buttonPanel;
    private static JButton selectUser, selectOwner, selectVC;

    private static void setButtons() {
        selectUser = new JButton("User");
        selectOwner = new JButton("Owner");
        selectVC = new JButton("VC");

        Color buttonColor = new Color(100, 150, 250);
        selectUser.setBackground(buttonColor);
        selectUser.setForeground(new Color(240, 240, 240));
        selectOwner.setBackground(buttonColor);
        selectOwner.setForeground(new Color(240, 240, 240));
        selectVC.setBackground(buttonColor);
        selectVC.setForeground(new Color(240, 240, 240));

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        selectUser.setFont(buttonFont);
        selectOwner.setFont(buttonFont);
        selectVC.setFont(buttonFont);

        selectUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new user(); 
            }
        });

        selectOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new owner(); 
            }
        });

        selectVC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new vc();
            }
        });
    }

    private static void setButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(selectOwner);
        buttonPanel.add(selectUser);
        buttonPanel.add(selectVC); 
    }

    private static void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeMessage, userSelectMessage;
        welcomeMessage = new JLabel("Welcome to VCRTS!");
        userSelectMessage = new JLabel("I'm a");

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        welcomeMessage.setFont(labelFont);
        userSelectMessage.setFont(new Font("Arial", Font.PLAIN, 16));

        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        userSelectMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(welcomeMessage);
        mainPanel.add(userSelectMessage);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }

    public static void setMainFrame() {
        mainFrame = new JFrame("VCRTS Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(true);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
    }

    public static void getMainFrame() {
        setButtons();
        setButtonPanel();
        setMainPanel();
        setMainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        getMainFrame();
    }
}

