package frontend.vc;

import backend.dashboard.AdminDashboard;
import frontend.main;

import backend.dashboard.AdminDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class vc_dash extends JFrame {
    private JButton viewJobsButton, calculateCompletionTimeButton, backButton;
<<<<<<< HEAD:src/frontend/vc_dash.java
    private List<Job> jobs = new ArrayList<>();
=======
>>>>>>> subbranch-Leon:src/frontend/vc/vc_dash.java
    private AdminDashboard adminDashboard = new AdminDashboard();

    public vc_dash() {
        createDashboard();
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
        viewJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,adminDashboard.displayJobList());
            }
        });

        calculateCompletionTimeButton = new JButton("Calculate Completion Time");
        calculateCompletionTimeButton.setFont(labelFont);
        calculateCompletionTimeButton.setBackground(buttonColor);
        calculateCompletionTimeButton.setForeground(Color.WHITE);
        calculateCompletionTimeButton.setFocusPainted(false);
        calculateCompletionTimeButton.addActionListener(this::calculateCompletionTime);

        backButton = new JButton("Back");
        backButton.setFont(labelFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> backToMain());

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; 
        mainPanel.add(viewJobsButton, gbc);

        gbc.gridy++;
        mainPanel.add(calculateCompletionTimeButton, gbc);

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
<<<<<<< HEAD:src/frontend/vc_dash.java
        adminDashboard.readJobsFromFile("user_transaction.txt");
        adminDashboard.parse(adminDashboard.jobs.toString());
        JOptionPane.showMessageDialog(this, adminDashboard.getJobSummary());
    }

    private void backToMain() {
        main.getMainFrame();
        dispose();
=======
        JOptionPane.showMessageDialog(this, adminDashboard.getJobSummary());
    }

    private void signOut() {
        main.getMainFrame(); // Go back to the main frame
        dispose(); // Close the backend.login frame
>>>>>>> subbranch-Leon:src/frontend/vc/vc_dash.java
    }


    private static class Job {
        private final String id;
        private final int duration;

        public Job(String id, int duration) {
            this.id = id;
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public int getDuration() {
            return duration;
        }
    }
}
