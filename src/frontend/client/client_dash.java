package frontend.client;

import backend.dashboard.ClientDashboard;
import backend.job.Job;
import backend.login.User;
import backend.master.NumericTextField;
import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class client_dash extends JFrame {
    private JTextField jobNameField, jobDurationField, jobDeadlineField;
    private JButton submitButton, clearButton, signOutButton, helpButton;
    private Job job;
    private User user;
    private ClientDashboard clientDashboard = new ClientDashboard();
    private LocalDate deadline = LocalDate.of(2024,12,1);

    //these are the client-server components
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public client_dash(User user) {
        createClientDash(user);
    }

    public void createClientDash(User user) {
        this.user=user;
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

        JLabel jobNameLabel = new JLabel("Job Name:");
        jobNameLabel.setFont(labelFont);
        jobNameField = new JTextField();
        jobNameField.setFont(fieldFont);

        JLabel jobDurationLabel = new JLabel("Job Duration (mins):");
        jobDurationLabel.setFont(labelFont);
        jobDurationField = new NumericTextField(10);
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
        mainPanel.add(jobNameLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobNameField, gbc);

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
    // Validate input fields
    if (jobNameField.getText().isEmpty() || jobDurationField.getText().isEmpty() || 
        jobDeadlineField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        return;
    }

    try {
        // Create job object
        job = clientDashboard.addJob(jobNameField.getText(), user.getUserId(),
                Duration.ofMinutes(Integer.parseInt(jobDurationField.getText())), deadline);
        
        // Save to file
        saveToFile(job.toString());

        // Connect to server and submit job
        try (Socket socket = new Socket("localhost", 25565);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Format job data
            String jobData = String.format("%s,%s,%s",
                jobNameField.getText(),
                jobDurationField.getText(),
                jobDeadlineField.getText());

            // Send job data
            out.println(jobData);

            // Wait for response
            String response = in.readLine();
            if (response != null && !response.isEmpty()) {
                JOptionPane.showMessageDialog(this, response);
                if (response.contains("submitted successfully")) {
                    clearFields(); // Clear fields only on successful submission
                }
            } else {
                JOptionPane.showMessageDialog(this, "No response from server.");
            }
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter a valid number for duration.");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
    }
}


    private void clearFields() {
        jobNameField.setText("");
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
        try (FileWriter writer = new FileWriter("client_transaction.txt", true)) {
            writer.write(data + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

}
