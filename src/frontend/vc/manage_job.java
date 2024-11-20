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

        JPanel buttonsPanel = new JPanel(new GridLayout(0, 1, 1, 1));

        Dimension buttonSize = new Dimension(1, 1);

        JButton acceptButton = createButton("Accept Selected", buttonSize);
        JButton rejectButton = createButton("Reject Selected", buttonSize);
        JButton acceptAllButton = createButton("Accept All", buttonSize);
        JButton rejectAllButton = createButton("Reject All", buttonSize);
        JButton completionTimeButton = createButton("Completion Time", buttonSize);
        JButton writeToFileButton = createButton("Write to File", buttonSize);
        JButton backButton = createButton("Back", buttonSize);

        buttonsPanel.add(acceptButton);
        buttonsPanel.add(rejectButton);
        buttonsPanel.add(acceptAllButton);
        buttonsPanel.add(rejectAllButton);
        buttonsPanel.add(completionTimeButton);
        buttonsPanel.add(writeToFileButton);
        buttonsPanel.add(backButton);

        acceptButton.addActionListener(e -> acceptSelected());
        rejectButton.addActionListener(e -> rejectSelected());
        acceptAllButton.addActionListener(e -> acceptAll());
        rejectAllButton.addActionListener(e -> rejectAll());

        completionTimeButton.addActionListener(e -> getJobSummary());

        add(listPanel);
        add(buttonsPanel);

        setVisible(true);
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
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
            ) {
                String jobData;
                while ((jobData = in.readLine()) != null) {
                    String[] jobAttributes = jobData.split(",");
                    SwingUtilities.invokeLater(() -> incomingModel.addRow(jobAttributes));
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




