package frontend.client;

import backend.dashboard.ClientDashboard;
import backend.job.Job;
import backend.login.User;
import backend.master.NumericTextField;
import com.toedter.calendar.JDateChooser;
import frontend.main.userMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class client_dash extends JFrame {
    private JTextField jobNameField, jobDurationField, jobDeadlineField;
    private JButton submitButton, clearButton, signOutButton, helpButton;
    private Job job;
    private User user;
    private ClientDashboard clientDashboard = new ClientDashboard();
    private JDateChooser dateChooser = new JDateChooser();


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

        JLabel jobNameLabel = new JLabel("Job ID:");
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

        signOutButton = new JButton("Back");
        signOutButton.setFont(labelFont);
        signOutButton.setBackground(buttonColor);
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setFocusPainted(false);
        signOutButton.addActionListener(e -> {
            try {
                signOut();
            } catch (UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });

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
        mainPanel.add(dateChooser, gbc);

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
        Date selectedDate = dateChooser.getDate();
        LocalDate localDate = selectedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        job = clientDashboard.addJob(jobNameField.getText(), user.getUserId(), Duration.ofMinutes(Integer.parseInt(jobDurationField.getText())), localDate);
        saveToFile(job.toString());
        try (Socket socket = new Socket("localhost", 25565);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeUTF(job.toString());

            JOptionPane.showMessageDialog(this, "Job sent to server! Waiting for Controller...");
            jobNameField.setEnabled(false);
            jobDurationField.setEnabled(false);
            jobDeadlineField.setEnabled(false);
            submitButton.setEnabled(false);

            String response = in.readUTF();
            JOptionPane.showMessageDialog(this, "Admin Response: " + response);

            if (response.equalsIgnoreCase("Rejected")) {
                jobNameField.setEnabled(true);
                jobDurationField.setEnabled(true);
                jobDeadlineField.setEnabled(true);
                submitButton.setEnabled(true);
            } else {
                jobNameField.setText("");
                jobDurationField.setText("");
                jobDeadlineField.setText("");
                jobNameField.setEnabled(true);
                jobDurationField.setEnabled(true);
                jobDeadlineField.setEnabled(true);
                submitButton.setEnabled(true);
                saveToFile(job.toString());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to server.");
        }
    }

    private void clearFields() {
        jobNameField.setText("");
        jobDurationField.setText("");
        jobDeadlineField.setText("");
    }

    private void signOut() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new userMain(user);
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
