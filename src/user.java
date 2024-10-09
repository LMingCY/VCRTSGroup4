import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class user extends JFrame{
    JLabel welcomeLabel = new JLabel("Welcome back!");
    JLabel loginPromptLabel = new JLabel("Please login:");

    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField(15);

    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(15);

    JButton loginButton = new JButton("Login");
    JButton backButton = new JButton("Back");

    public user() {
        // Set frame title
        super("Login");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add components to the frame

        // Welcome back and Please login labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        add(welcomeLabel, gbc);

        gbc.gridy = 1;
        add(loginPromptLabel, gbc);

        // Username label and text field
        gbc.gridwidth = 1; // Reset grid width to 1 column
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password label and text field
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login button
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Back button at the bottom
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(backButton, gbc);

        // Set default operations for the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        // Action listeners for buttons (you can expand this for actual login logic)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                JOptionPane.showMessageDialog(null, "Logging in as " + username);
                new user_dash();
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.getMainFrame();
                setVisible(false);
            }
        });
    }

}