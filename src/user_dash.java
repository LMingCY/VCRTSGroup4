import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;

public class user_dash extends JFrame {
    private JTextField userIDField, jobDurationField, jobDeadlineField;
    private JButton submitButton, clearButton, signOutButton, helpButton;

    public user_dash() {
        setTitle("Vehicular Cloud Console");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color buttonColor = new Color(100, 150, 250);

        JLabel userIDLabel = new JLabel("Client ID:");
        userIDLabel.setFont(labelFont);
        userIDField = new JTextField();
        userIDField.setFont(fieldFont);

        JLabel jobDurationLabel = new JLabel("Job Duration (minutes) :");
        jobDurationLabel.setFont(labelFont);
        jobDurationField = new JTextField();
        jobDurationField.setFont(fieldFont);

        JLabel jobDeadlineLabel = new JLabel("Job Deadline:");
        jobDeadlineLabel.setFont(labelFont);
        jobDeadlineField = new JTextField();
        jobDeadlineField.setFont(fieldFont);

        submitButton = new JButton("Submit");
        submitButton.setFont(labelFont);
        submitButton.setBackground(buttonColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(this::submitData);

        clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);
        clearButton.setBackground(buttonColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearFields());

        signOutButton = new JButton("Sign Out");
        signOutButton.setFont(labelFont);
        signOutButton.setBackground(buttonColor);
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setFocusPainted(false);
        signOutButton.addActionListener(e -> signOut());

        helpButton = new JButton("Help");
        helpButton.setFont(labelFont);
        helpButton.setBackground(buttonColor);
        helpButton.setForeground(Color.WHITE);
        helpButton.setFocusPainted(false);
        helpButton.addActionListener(e -> showHelp());

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(userIDLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(userIDField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(jobDurationLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobDurationField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(jobDeadlineLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobDeadlineField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitButton, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(clearButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(signOutButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        mainPanel.add(helpButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void submitData(ActionEvent e) {
        String userData = "User ID: " + userIDField.getText() +
                ", Job Duration (minutes): " + jobDurationField.getText() +
                ", Job Deadline: " + jobDeadlineField.getText();
        String timestamp = LocalDateTime.now().toString();
        saveToFile(userData + "\nTimestamp: " + timestamp);
        JOptionPane.showMessageDialog(this, "Entries saved!");
    }

    private void clearFields() {
        userIDField.setText("");
        jobDurationField.setText("");
        jobDeadlineField.setText("");
    }

    private void signOut() {
        main.getMainFrame();
        dispose();
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(this, "Enter valid details and click submit.");
    }

    private void saveToFile(String data) {
        try (FileWriter writer = new FileWriter("user_transaction.txt", true)) {
            writer.write(data + "\n\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }
}
