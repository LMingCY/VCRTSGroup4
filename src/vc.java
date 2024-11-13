import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vc extends JFrame {
    
    JLabel welcomeLabel, loginPromptLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, backButton, acceptButton, rejectButton;

    public vc() {
        
        super("Vehicular Cloud Login");
        setSize(400, 400);
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

        welcomeLabel = new JLabel("Welcome to Vehicular Cloud!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginPromptLabel = new JLabel("Please log in:");
        loginPromptLabel.setFont(labelFont);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);

        usernameField = new JTextField(15);
        usernameField.setFont(fieldFont);
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

        acceptButton = new JButton("Accept");
        acceptButton.setBackground(new Color(34, 139, 34)); 
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFont(labelFont);

        rejectButton = new JButton("Reject");
        rejectButton.setBackground(new Color(220, 20, 60)); 
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(labelFont);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);
        gbc.gridy++;
        panel.add(loginPromptLabel, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(backButton, gbc);

        gbc.gridy++;
        panel.add(acceptButton, gbc); // Add Accept button
        gbc.gridy++;
        panel.add(rejectButton, gbc); // Add Reject button

        add(panel);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("vcuser") && password.equals("vcpass")) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    new vc_dash();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
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

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Data accepted and ready to be stored.");
                // Add data storage logic here
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Data rejected and will not be stored.");
            }
        });
    }

    public static void main(String[] args) {
        new vc();
    }
}

