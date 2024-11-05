import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class vcDashLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;

    public vcDashLogin() {
        setTitle("VC Dashboard Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 240, 240));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(100, 150, 250);

        JLabel welcomeLabel = new JLabel("Welcome to VC Dashboard!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(welcomeLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(labelFont);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(labelFont);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> attemptLogin());

        backButton = new JButton("Back");
        backButton.setFont(labelFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> backToMain());

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        gbc.gridy++;
        loginPanel.add(backButton, gbc);

        add(loginPanel);
        setVisible(true);
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
       
        if (username.equals("vcuser") && password.equals("vcpass")) { // Example credentials
            JOptionPane.showMessageDialog(this, "Login successful.");
            new vc_dash(); // Open the main dashboard
            dispose(); // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void backToMain() {
        main.getMainFrame(); // Return to main application frame
        dispose();
    }

    public static void main(String[] args) {
        new vcDashLogin();
    }
}

class vc_dash extends JFrame {
    private JTextField jobIDField, jobDurationField;
    private JButton addJobButton, calculateCompletionTimeButton, clearButton, backButton;
    private List<Integer> jobDurations = new ArrayList<>();

    public vc_dash() {
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
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color buttonColor = new Color(100, 150, 250);

        JLabel jobIDLabel = new JLabel("Job ID:");
        jobIDLabel.setFont(labelFont);
        jobIDField = new JTextField();
        jobIDField.setFont(fieldFont);

        JLabel jobDurationLabel = new JLabel("Job Duration (minutes):");
        jobDurationLabel.setFont(labelFont);
        jobDurationField = new JTextField();
        jobDurationField.setFont(fieldFont);

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
        backButton.addActionListener(e -> backToMain());

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(jobIDLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobIDField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(jobDurationLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobDurationField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
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

    private void backToMain() {
        main.getMainFrame(); // Return to main application frame
        dispose();
    }
}
