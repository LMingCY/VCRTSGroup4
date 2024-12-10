package frontend.main;

import backend.login.User;
import frontend.client.client_dash;
import frontend.owner.owner_dash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userMain {
    private JFrame mainFrame;
    private JPanel mainPanel;
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

        selectUser.setFocusPainted(false);
        selectOwner.setFocusPainted(false);

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
                mainFrame.dispose();
                new owner_dash(user);
            }
        });
    }

    private void setMainPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome to VCRTS!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(welcomeLabel, gbc);

        JLabel generalWelcomeMessage = new JLabel("Welcome to our VCRTS! Use our features to either rent out your vehicle or post a job!");
        generalWelcomeMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        generalWelcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy++;
        mainPanel.add(generalWelcomeMessage, gbc);

        JLabel userWelcomeMessage = new JLabel("Welcome, " + user.getName() + "!");
        userWelcomeMessage.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy++;
        mainPanel.add(userWelcomeMessage, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(selectOwner);
        buttonPanel.add(selectUser);

        gbc.gridy++;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
    }

    public void setMainFrame() {
        mainFrame = new JFrame("VCRTS Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setResizable(true);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void getMainFrame() {
        setButtons();
        setMainPanel();
        setMainFrame();
    }
}

