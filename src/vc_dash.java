import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class vc_dash extends JFrame {
    private JTextField jobIDField, jobDurationField;
    private JButton addJobButton, calculateCompletionTimeButton, clearButton, backButton;
    private List<Integer> jobDurations = new ArrayList<>();

    public vc_dash() {
        // Start with the login dialog
        if (showLoginDialog()) {
            createDashboard();
        } else {
            System.exit(0); // Exit if login fails
        }
    }

    private boolean showLoginDialog() {
        JLabel welcomeLabel = new JLabel("Welcome to VC!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        // Create a panel for the login dialog
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to the panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(welcomeLabel, gbc);
        gbc.gridy++;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);
        gbc.gridy++;
        loginPanel.add(backButton, gbc);

        // Create the login dialog
        int option = JOptionPane.showConfirmDialog(null, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) {
            return false; // User cancelled
        }

        // Handle login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("vcuser") && password.equals("vcpass")) { // Example credentials
                    JOptionPane.showMessageDialog(null, "Login successful");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                    System.exit(0); // Exit on failed login
                }
            }
        });

        // Handle back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit if back is pressed
            }
        });

        return true; // Login successful
    }

    private void createDashboard() {
        setTitle("Vehicular Cloud RTS - Controller Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(100, 150, 250);

        JLabel jobIDLabel = new JLabel("Job ID:");
        jobIDLabel.setFont(labelFont);
        jobIDField = new JTextField();
        jobIDField.setFont(labelFont);

        JLabel jobDurationLabel = new JLabel("Job Duration (minutes):");
        jobDurationLabel.setFont(labelFont);
        jobDurationField = new JTextField();
        jobDurationField.setFont(labelFont);

        addJobButton = new JButton("Add Job");
        addJobButton.setFont(labelFont);
        addJobButton.setBackground(buttonColor);
        addJobButton.setForeground(Color.WHITE);
        addJobButton.setFocusPainted(false);
        addJobButton.addActionListener(this::addJob);

        calculateCompletionTimeButton = new JButton("Calculate Completion Time");
        calculateCompletionTimeButton.setFont(labelFont);
        calculateCompletionTimeButton.setBackground(buttonColor);
        calculateCompletionTimeButton.setForeground(Color.WHITE);
        calculateCompletionTimeButton.setFocusPainted(false);
        calculateCompletionTimeButton.addActionListener(this::calculateCompletionTime);

        clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);
        clearButton.setBackground(buttonColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearFields());

        backButton = new JButton("Back");
        backButton.setFont(labelFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> signOut());

        // Add components to the panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(jobIDLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobIDField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(jobDurationLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobDurationField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        mainPanel.add(addJobButton, gbc);

        gbc.gridy++;
        mainPanel.add(calculateCompletionTimeButton, gbc);

        gbc.gridy++;
        mainPanel.add(clearButton, gbc);

        gbc.gridy++;
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void addJob(ActionEvent e) {
        try {
            int duration = Integer.parseInt(jobDurationField.getText());
            jobDurations.add(duration);
            JOptionPane.showMessageDialog(this, "Job added with duration " + duration + " minutes.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid duration.");
        }
    }

    private void calculateCompletionTime(ActionEvent e) {
        List<Integer> completionTimes = new ArrayList<>();
        int currentTime = 0;

        for (int duration : jobDurations) {
            currentTime += duration;
            completionTimes.add(currentTime);
        }

        StringBuilder message = new StringBuilder("Completion Times: ");
        for (int time : completionTimes) {
            message.append(time).append(" ");
        }

        JOptionPane.showMessageDialog(this, message.toString());
    }

    private void clearFields() {
        jobIDField.setText("");
        jobDurationField.setText("");
    }

    private void signOut() {
        System.exit(0); // Sign out action
    }

    public static void main(String[] args) {
        new vc_dash();
    }
}
