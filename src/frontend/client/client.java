package frontend.client;

import backend.MySQL.Driver;
import backend.login.User;
import backend.master.Idgenerator;
import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class client extends JFrame {
    // Declare UI components
    JLabel welcomeLabel, loginPromptLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, backButton;
    Driver db = new Driver();
    Idgenerator idgenerator;

    public client() {
        // Set up the main frame properties
        super("User Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null); // Center the window on the screen

        // Define color scheme and fonts for UI consistency
        Color buttonColor = new Color(100, 150, 250);
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Create main panel with padding and background color
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set component padding

        // Initialize labels with specific fonts
        welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginPromptLabel = new JLabel("Please login:");
        loginPromptLabel.setFont(labelFont);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);

        // Initialize text fields for username and password
        usernameField = new JTextField(15);
        usernameField.setFont(fieldFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);

        // Initialize buttons with colors and fonts, and add action listeners
        loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(labelFont);

        backButton = new JButton("Back");
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(labelFont);

        // Position and add components to the panel using GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc); // Welcome message at the top
        gbc.gridy++;
        panel.add(loginPromptLabel, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc); // Username label
        gbc.gridx = 1;
        panel.add(usernameField, gbc); // Username field

        gbc.gridx = 0; gbc.gridy++;
        panel.add(passwordLabel, gbc); // Password label
        gbc.gridx = 1;
        panel.add(passwordField, gbc); // Password field

        // Position buttons and center them
        gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(backButton, gbc);

        add(panel);
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
                    new client_dash(user);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.getMainFrame();
                dispose();
            }
        }); 
        /* backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = idgenerator.generateUserId();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = new User(userId,username,password,"Jack","jack@gmail.com");
                user.registerUser(userId,username,password,"Jack","jack@gmail.com");
            }
        }); */ // this is the registration button
    }

}
