package frontend.vc;

import backend.dashboard.AdminDashboard;
import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class vc_dash extends JFrame {
    private JButton viewJobsButton, calculateCompletionTimeButton, backButton, acceptButton, rejectButton;
    private AdminDashboard adminDashboard = new AdminDashboard();
    //these are client-server components
    private ServerSocket server;
    private Socket admin;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public vc_dash() {
        try {
            server = new ServerSocket(25565);
            admin = new Socket("localhost", 25565);
            admin = server.accept();
            createDashboard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDashboard() {


        adminDashboard.readJobsFromFile("client_transaction.txt");
        adminDashboard.parse(adminDashboard.getJobs().toString());
        setTitle("Vehicular Cloud RTS - Controller Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; 

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(100, 150, 250);

        viewJobsButton = new JButton("View Jobs");
        viewJobsButton.setFont(labelFont);
        viewJobsButton.setBackground(buttonColor);
        viewJobsButton.setForeground(Color.WHITE);
        viewJobsButton.setFocusPainted(false);
        viewJobsButton.addActionListener(this::displayJobList);

        calculateCompletionTimeButton = new JButton("Calculate Completion Time");
        calculateCompletionTimeButton.setFont(labelFont);
        calculateCompletionTimeButton.setBackground(buttonColor);
        calculateCompletionTimeButton.setForeground(Color.WHITE);
        calculateCompletionTimeButton.setFocusPainted(false);
        calculateCompletionTimeButton.addActionListener(this::calculateCompletionTime);

        acceptButton = new JButton("Accept");
        acceptButton.setBackground(new Color(34, 139, 34));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFont(labelFont);

        rejectButton = new JButton("Reject");
        rejectButton.setBackground(new Color(220, 20, 60));
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(labelFont);

        backButton = new JButton("Back");
        backButton.setFont(labelFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> signOut());

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; 
        mainPanel.add(viewJobsButton, gbc);

        gbc.gridy++;
        mainPanel.add(calculateCompletionTimeButton, gbc);

        gbc.gridy++;
        mainPanel.add(acceptButton, gbc);
        gbc.gridy++;
        mainPanel.add(rejectButton, gbc);

        gbc.gridy++;
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    /*
    private void viewJobs(ActionEvent e) {
        if (jobs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No jobs available.");
        } else {
            StringBuilder jobList = new StringBuilder("Current Jobs:\n");
            for (Job job : jobs) {
                jobList.append("Job ID: ").append(job.getId()).append(", Duration: ").append(job.getDuration()).append(" minutes\n");
            }
            JOptionPane.showMessageDialog(this, jobList.toString());
        }
    }

     */

    private void calculateCompletionTime(ActionEvent e) {
        JOptionPane.showMessageDialog(this, adminDashboard.getJobSummary());
    }
    private void displayJobList(ActionEvent e) {
        JOptionPane.showMessageDialog(null,adminDashboard.displayJobList());
    }

    private void signOut() {
        main.getMainFrame(); // Go back to the main frame
        dispose(); // Close the backend.login frame
    }

}
