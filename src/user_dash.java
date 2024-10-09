import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;

public class user_dash extends JFrame implements ActionListener {
    private JTextField userIDField, jobDurationField, jobDeadlineField;
    private JButton submitButton, clearButton, signOutButton, helpButton;
    private JPanel mainPanel, buttonPanel;

    public user_dash() {
        setTitle("Vehicular Cloud Console");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Client fields
        JLabel userIDLabel = new JLabel("Client ID:");
        JLabel jobDurationLabel = new JLabel("Job Duration:");
        JLabel jobDeadlineLabel = new JLabel("Job Deadline:");

        userIDField = new JTextField();
        jobDurationField = new JTextField();
        jobDeadlineField = new JTextField();

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        userIDLabel.setFont(labelFont);
        jobDurationLabel.setFont(labelFont);
        jobDeadlineLabel.setFont(labelFont);

        mainPanel.add(userIDLabel);
        mainPanel.add(userIDField);
        mainPanel.add(jobDurationLabel);
        mainPanel.add(jobDurationField);
        mainPanel.add(jobDeadlineLabel);
        mainPanel.add(jobDeadlineField);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        signOutButton = new JButton("Sign Out");
        helpButton = new JButton("Help");

       
        customizeButton(submitButton);
        customizeButton(clearButton);
        customizeButton(signOutButton);
        customizeButton(helpButton);

        // Adding action listeners
        submitButton.addActionListener(this);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userIDField.setText("");
                jobDurationField.setText("");
                jobDeadlineField.setText("");
            }
        });
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userIDField.setText("");
                jobDurationField.setText("");
                jobDeadlineField.setText("");
                main.getMainFrame(); 
                dispose();
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Enter valid details and click submit.");
            }
        });

        // Adding buttons to button panel
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(signOutButton);
        buttonPanel.add(helpButton);

        // Adding panels to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(100, 150, 250)); 
        button.setForeground(Color.WHITE);              
        button.setFont(new Font("Arial", Font.BOLD, 14)); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userData = "User ID: " + userIDField.getText() +
                    ", Job Duration: " + jobDurationField.getText() +
                    ", Job Deadline: " + jobDeadlineField.getText();
            String timestamp = LocalDateTime.now().toString();
            saveToFile(userData + "\nTimestamp: " + timestamp);
            JOptionPane.showMessageDialog(this, "Entries saved!");
        }
    }

    private void saveToFile(String data) {
        try (FileWriter writer = new FileWriter("user_transaction.txt", true)) {
            writer.write(data + "\n\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }
}