import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class vc_dash extends JFrame {
    private JTextField jobIDField, jobDurationField;
    private JButton viewJobsButton, calculateCompletionTimeButton, clearButton, backButton;
    private List<Job> jobs = new ArrayList<>(); // Store jobs as Job objects

    public vc_dash() {
        createDashboard();
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
        gbc.anchor = GridBagConstraints.CENTER; // Center all components

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(100, 150, 250);

        JLabel jobIDLabel = new JLabel("Job ID:");
        jobIDLabel.setFont(labelFont);
        jobIDField = new JTextField(10);
        jobIDField.setFont(labelFont);

        JLabel jobDurationLabel = new JLabel("Job Duration (minutes):");
        jobDurationLabel.setFont(labelFont);
        jobDurationField = new JTextField(10);
        jobDurationField.setFont(labelFont);

        viewJobsButton = new JButton("View Jobs");
        viewJobsButton.setFont(labelFont);
        viewJobsButton.setBackground(buttonColor);
        viewJobsButton.setForeground(Color.WHITE);
        viewJobsButton.setFocusPainted(false);
        viewJobsButton.addActionListener(this::viewJobs);

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
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; // Center button
        mainPanel.add(viewJobsButton, gbc);

        gbc.gridy++;
        mainPanel.add(calculateCompletionTimeButton, gbc);

        gbc.gridy++;
        mainPanel.add(clearButton, gbc);

        gbc.gridy++;
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

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

    private void calculateCompletionTime(ActionEvent e) {
        List<Integer> completionTimes = new ArrayList<>();
        int currentTime = 0;

        for (Job job : jobs) {
            currentTime += job.getDuration();
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

    // Job class to store job information
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
