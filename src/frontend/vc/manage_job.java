package frontend.vc;

import backend.job.Job;
import backend.login.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class manage_job extends JFrame {
    private static manage_job instance;
    private DefaultTableModel incomingModel;
    private DefaultTableModel acceptedModel;
    private JTable incomingTable;
    private JTable acceptedTable;
    private static final int port = 25565;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private User admin;


    public manage_job(User admin) {
        this.admin=admin;
        setTitle("Job Manager");
        setSize(600, 500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLocationRelativeTo(null);
        setupUI();

        startServer();
    }
    public static manage_job getInstance(User admin) {
        if (instance == null) {
            instance = new manage_job(admin);
        }
        return instance;
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
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        backButton.addActionListener(e -> backToMenu());

        add(listPanel);
        add(buttonsPanel);

        setVisible(true);
    }

    private void backToMenu() {
        dispose();
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
        private int jobId, clientId, jobStatus;
        private String jobName, jobResult;
        private Duration jobDuration;
        private LocalDate jobDeadline;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

                //200000002,abc,100000004,50,2024-12-01,1,,2024-11-19T22:48:52.240581400
                String jobData = in.readUTF();
                String[] jobParts = jobData.split(",");
                jobId = Integer.parseInt(jobParts[0]);
                jobName = jobParts[1];
                clientId = Integer.parseInt(jobParts[2]);
                jobDuration = Duration.ofMinutes(Integer.parseInt(jobParts[3]));
                jobDeadline = LocalDate.parse(jobParts[4]);
                jobStatus = Integer.parseInt(jobParts[5]);
                jobResult = jobParts[6];

                Job job = new Job(jobId, clientId, jobName, jobStatus, jobResult, jobDeadline, jobDuration);
                SwingUtilities.invokeLater(() -> incomingModel.addRow(jobParts));

                while (true) {
                    Thread.sleep(100);
                    if (isJobAccepted(jobId)) {
                        out.writeUTF("Accepted");
                        saveToFile(job.toString());
                        break;
                    } else if (isJobRejected(jobId)) {
                        out.writeUTF("Rejected");
                        break;
                    }
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
        }

        private boolean isJobAccepted(int jobId) {
            return findRowIndex(acceptedModel, String.valueOf(jobId)) != -1;
        }

        private boolean isJobRejected(int jobId) {
            return findRowIndex(incomingModel, String.valueOf(jobId)) == -1 && findRowIndex(acceptedModel, String.valueOf(jobId)) == -1;
        }

        private int findRowIndex(DefaultTableModel model, String jobId) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(jobId)) {
                    return i;
                }
            }
            return -1;
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
    private void saveToFile(String data) {
        try (FileWriter writer = new FileWriter("client_transaction_accepted_by_vc.txt", true)) {
            writer.write(data + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }
}




