package frontend.vc;

import backend.dashboard.AdminDashboard;
import backend.login.User;
import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class vc_dash extends JFrame {
    private JLabel adminInfoLabel;
    private JButton logoutButton;
    private JButton manageJobsButton;
    private JButton manageVehiclesButton;
    private JButton manageUsersButton;
    private JButton serverButton;
    private AdminDashboard adminDashboard = new AdminDashboard();
    //these are client-server components
    private ServerSocket server;
    private Socket admin;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public vc_dash(User admin) {
        createDashboard(admin);
    }

    private void createDashboard(User admin) {


        adminDashboard.readJobsFromFile("client_transaction.txt");
        adminDashboard.parse(adminDashboard.getJobs().toString());
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        adminInfoLabel = new JLabel("<html>Admin Name: " + admin.getName() + "<br>Admin ID: " + admin.getUserId() + "<br>Status: " + "</html>");
        topPanel.add(adminInfoLabel, BorderLayout.WEST);

        logoutButton = new JButton("Log Out");
        topPanel.add(logoutButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(buttonPanel, BorderLayout.CENTER);

        manageJobsButton = new JButton("Manage Jobs");
        manageJobsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageJobsButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageJobsButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        manageVehiclesButton = new JButton("Manage Vehicles");
        manageVehiclesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageVehiclesButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageVehiclesButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageUsersButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(manageUsersButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        serverButton = new JButton("Server");
        serverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        serverButton.setMaximumSize(new Dimension(200, 30));
        buttonPanel.add(serverButton);

        setupListeners();
        setVisible(true);
    }

    private void setupListeners() {
        logoutButton.addActionListener(e -> {
            dispose();
            main.getMainFrame();
        });
        manageJobsButton.addActionListener(e -> {
            manage_job jobManager = manage_job.getInstance();
            jobManager.setVisible(true);
        });
        manageVehiclesButton.addActionListener(e -> {
            manage_vehicle vehicleManager = manage_vehicle.getInstance();
            vehicleManager.setVisible(true);
        });

    }

}
