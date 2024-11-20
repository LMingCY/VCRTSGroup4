package frontend.vc;

import backend.job.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class manage_job extends JFrame {
    private DefaultTableModel incomingModel;
    private DefaultTableModel acceptedModel;
    private JTable incomingTable;
    private JTable acceptedTable;
    private static final int port = 25565;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    public manage_job() {
        setTitle("Job Manager");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLocationRelativeTo(null);
        setupUI();

        startServer();
    }

    private void setupUI() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        incomingModel = new DefaultTableModel();
        acceptedModel = new DefaultTableModel();

        String[] columns = {"Job ID", "Name", "Client ID", "Duration", "Deadline"};
        incomingModel.setColumnIdentifiers(columns);
        acceptedModel.setColumnIdentifiers(columns);

        incomingTable = new JTable(incomingModel);
        acceptedTable = new JTable(acceptedModel);

        JScrollPane incomingScrollPane = new JScrollPane(incomingTable);
        JScrollPane acceptedScrollPane = new JScrollPane(acceptedTable);

        listPanel.add(new JLabel("Incoming Jobs"));
        listPanel.add(incomingScrollPane);
        listPanel.add(new JLabel("Accepted Jobs"));
        listPanel.add(acceptedScrollPane);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonsPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(100, 150, 250);

        JButton acceptButton = createStyledButton("Accept Selected", buttonFont, buttonColor);
        JButton rejectButton = createStyledButton("Reject Selected", buttonFont, buttonColor);
        JButton acceptAllButton = createStyledButton("Accept All", buttonFont, buttonColor);
        JButton rejectAllButton = createStyledButton("Reject All", buttonFont, buttonColor);
        JButton completionTimeButton = createStyledButton("Completion Time", buttonFont, buttonColor);
        JButton writeToFileButton = createStyledButton("Write to File", buttonFont, buttonColor);
        JButton backButton = createStyledButton("Back", buttonFont, buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(acceptButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(rejectButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(acceptAllButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(rejectAllButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(completionTimeButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(writeToFileButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(backButton, gbc);

        acceptButton.addActionListener(e -> acceptSelected());
        rejectButton.addActionListener(e -> rejectSelected());
        acceptAllButton.addActionListener(e -> acceptAll());
        rejectAllButton.addActionListener(e -> rejectAll());
        completionTimeButton.addActionListener(e -> getJobSummary());

        add(listPanel);
        add(buttonsPanel);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void acceptSelected() {
        int selectedRow = incomingTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] rowData = new Object[5];
            for (int i = 0; i < 5; i++) {
                rowData[i] = incomingModel.getValueAt(selectedRow, i);
            }
            acceptedModel.addRow(rowData);
            incomingModel.removeRow(selectedRow);
        }
    }

    private void rejectSelected() {
        int selectedRow = incomingTable.getSelectedRow();
        if (selectedRow != -1) {
            incomingModel.removeRow(selectedRow);
        }
    }

    private void acceptAll() {
        int rowCount = incomingModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            Object[] rowData = new Object[5];
            for (int j = 0; j < 5; j++) {
                rowData[j] = incomingModel.getValueAt(i, j);
            }
            acceptedModel.addRow(rowData);
            incomingModel.removeRow(i);
        }
    }

    private void rejectAll() {
        incomingModel.setRowCount(0);
    }
    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    executorService.execute(new ClientHandler(clientSocket));
                }
            } catch (IOException e) {
                System.out.println("Error starting server: " + e.getMessage());
            }
        }).start();
    }

    private class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String jobData;
            while ((jobData = in.readLine()) != null) {
                String[] jobAttributes = jobData.split(",");
                String jobName = jobAttributes[1]; // Assume job name is at index 1
                String jobDuration = jobAttributes[3]; // Assume duration is at index 3

                // Simple logic to accept/reject the job
                int duration = Integer.parseInt(jobDuration.replaceAll("[^0-9]", ""));
                if (duration <= 60) {
                    SwingUtilities.invokeLater(() -> incomingModel.addRow(jobAttributes));
                    out.println("Job '" + jobName + "' has been accepted.");
                } else {
                    out.println("Job '" + jobName + "' has been rejected.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

    private void getJobSummary() {
        int rowCount = acceptedModel.getRowCount();
        int totalDuration = 0;

        for (int i = 0; i < rowCount; i++) {
            String durationText = (String) acceptedModel.getValueAt(i, 3);
            try {
                int duration = Integer.parseInt(durationText.split(" ")[0]);
                totalDuration += duration;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error parsing duration: " + durationText);
            }
        }

        JOptionPane.showMessageDialog(this, "Total Completion Time: " + totalDuration + " minutes");
    }
    public static void main(String[] args) {
        new manage_job();
    }
}




