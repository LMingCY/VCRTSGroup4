package frontend.vc;

import backend.dashboard.AdminDashboard;
import backend.login.User;
import frontend.main.initialLogin;

import javax.swing.*;
import java.awt.*;

public class vc_dash extends JFrame {
    private JLabel adminInfoLabel;
    private JButton logoutButton;
    private JButton manageJobsButton;
    private JButton manageVehiclesButton;
    private JButton manageUsersButton;
    private JButton serverButton;
    private AdminDashboard adminDashboard = new AdminDashboard();
    private User admin;

    public vc_dash(User admin) {
        createDashboard(admin);
        this.admin=admin;
    }

    private void createDashboard(User admin) {
        adminDashboard.readJobsFromFile("client_transaction.txt");
        adminDashboard.parse(adminDashboard.getJobs().toString());
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Consistent color and font
        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(100, 150, 250);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(backgroundColor);

        adminInfoLabel = new JLabel("<html>Admin Name: " + admin.getName() + "<br>Admin ID: " + admin.getUserId() + "<br>Status: </html>");
        adminInfoLabel.setFont(labelFont);
        topPanel.add(adminInfoLabel, BorderLayout.WEST);

        logoutButton = new JButton("Log Out");
        logoutButton.setBackground(buttonColor);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(buttonFont);
        logoutButton.setFocusPainted(false);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        manageJobsButton = new JButton("Manage Jobs");
        manageJobsButton.setBackground(buttonColor);
        manageJobsButton.setForeground(Color.WHITE);
        manageJobsButton.setFont(buttonFont);
        manageJobsButton.setFocusPainted(false);
        manageJobsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageJobsButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageJobsButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        manageVehiclesButton = new JButton("Manage Vehicles");
        manageVehiclesButton.setBackground(buttonColor);
        manageVehiclesButton.setForeground(Color.WHITE);
        manageVehiclesButton.setFont(buttonFont);
        manageVehiclesButton.setFocusPainted(false);
        manageVehiclesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageVehiclesButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageVehiclesButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setBackground(buttonColor);
        manageUsersButton.setForeground(Color.WHITE);
        manageUsersButton.setFont(buttonFont);
        manageUsersButton.setFocusPainted(false);
        manageUsersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageUsersButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageUsersButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        serverButton = new JButton("Server");
        serverButton.setBackground(buttonColor);
        serverButton.setForeground(Color.WHITE);
        serverButton.setFont(buttonFont);
        serverButton.setFocusPainted(false);
        serverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        serverButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(serverButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setupListeners();
        setVisible(true);
    }

    private void setupListeners() {
        logoutButton.addActionListener(e -> {
            dispose();
            new initialLogin();
        });
        manageJobsButton.addActionListener(e -> {
            manage_job jobManager = manage_job.getInstance(admin);
            jobManager.setVisible(true);
        });
        manageVehiclesButton.addActionListener(e -> {
            manage_vehicle vehicleManager = manage_vehicle.getInstance();
            vehicleManager.setVisible(true);
        });
    }
}
