package frontend.main;

import backend.MySQL.Driver;
import backend.login.User;
import frontend.misc.createAccount;
import frontend.vc.vc_dash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class initialLogin extends JFrame {
    JLabel welcomeLabel, generalWelcomeMessage, loginPromptLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, backButton;
    Driver db = new Driver();

    public initialLogin() {
        super("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        Color buttonColor = new Color(100, 150, 250);
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        welcomeLabel = new JLabel("Welcome to VCRTS!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        generalWelcomeMessage = new JLabel("<html><div style='text-align:center;'>Welcome to our VCRTS!<br>Use our features to either rent out your vehicle or post a job!</div></html>");
        generalWelcomeMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        loginPromptLabel = new JLabel("Please login:");
        loginPromptLabel.setFont(labelFont);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);

        usernameField = new JTextField(15);
        usernameField.setFont(fieldFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);

        loginButton = new JButton("         Login          ");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(labelFont);

        backButton = new JButton(" Create Account");
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(labelFont);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeLabel, gbc);

        gbc.gridy++;
        panel.add(generalWelcomeMessage, gbc);

        gbc.gridy++;
        panel.add(loginPromptLabel, gbc);

        gbc.gridy++; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy++;
        panel.add(backButton, gbc);

        add(panel);
        pack(); 
        setResizable(false);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = db.validateLogin(username, password);

                if (user != null) {
                    int userId = user.getUserId();
                    JOptionPane.showMessageDialog(null, "Logged in successfully as " + username + ", User ID: " + userId);
                    if (String.valueOf(userId).charAt(0) != '9') {
                        try {
                            new userMain(user);
                        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        new vc_dash(user);
                    }
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new createAccount();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new initialLogin();
    }
}

