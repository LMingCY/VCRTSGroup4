package frontend.owner;

import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class owner extends JFrame {
    // Declare UI components
    JLabel welcomeLabel, loginPromptLabel, ownernameLabel, passwordLabel;
    JTextField ownernameField;
    JPasswordField passwordField;
    JButton loginButton, backButton;

    public owner() {
        // Set up the frontend.main frame properties
        super("Owner Login");
        setSize(400, 350);
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

        welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginPromptLabel = new JLabel("Please Login:");
        loginPromptLabel.setFont(labelFont);

        ownernameLabel = new JLabel("Username:");
        ownernameLabel.setFont(labelFont);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);

        ownernameField = new JTextField(15);
        ownernameField.setFont(fieldFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);

        loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(labelFont);

        backButton = new JButton("Back");
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(labelFont);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc); 
        gbc.gridy++;
        panel.add(loginPromptLabel, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(ownernameLabel, gbc); 
        gbc.gridx = 1;
        panel.add(ownernameField, gbc); 

        gbc.gridx = 0; gbc.gridy++;
        panel.add(passwordLabel, gbc); 
        gbc.gridx = 1;
        panel.add(passwordField, gbc); 

        gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(backButton, gbc);

        add(panel);
        setVisible(true); 

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = ownernameField.getText();
                String password = new String(passwordField.getPassword());
                JOptionPane.showMessageDialog(null, "Logging in as " + username);
                new frontend.owner.owner_dash();
                setVisible(false); 
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the frontend.main frame
                main.getMainFrame();
                dispose(); // Close the backend.login frame
            }
        });
    }
}
