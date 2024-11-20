package frontend.vc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class manage_vehicle extends JFrame {
    private DefaultTableModel incomingModel;
    private DefaultTableModel activeModel;
    private JTable incomingTable;
    private JTable activeTable;
    private static final int port = 25566; 
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static manage_vehicle instance;

    public manage_vehicle() {
        setTitle("Vehicle Manager");
        setSize(600, 500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLocationRelativeTo(null);
        setupUI();

        startServer();
    }
    public static manage_vehicle getInstance() {
        if (instance == null) {
            instance = new manage_vehicle();
        }
        return instance;
    }

    private void setupUI() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        incomingModel = new DefaultTableModel();
        activeModel = new DefaultTableModel();

        String[] columns = {"Vehicle ID", "Make", "Model", "Owner ID", "Status", "Residency Time"};
        incomingModel.setColumnIdentifiers(columns);
        activeModel.setColumnIdentifiers(columns);

        incomingTable = new JTable(incomingModel);
        activeTable = new JTable(activeModel);

        JScrollPane incomingScrollPane = new JScrollPane(incomingTable);
        JScrollPane activeScrollPane = new JScrollPane(activeTable);

        listPanel.add(new JLabel("Incoming Vehicles"));
        listPanel.add(incomingScrollPane);
        listPanel.add(new JLabel("Active Vehicles"));
        listPanel.add(activeScrollPane);

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
        gbc.gridy++;
        buttonsPanel.add(writeToFileButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(backButton, gbc);


        acceptButton.addActionListener(e -> acceptSelected());
        rejectButton.addActionListener(e -> rejectSelected());
        acceptAllButton.addActionListener(e -> acceptAll());
        rejectAllButton.addActionListener(e -> rejectAll());

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
            Object[] rowData = new Object[6];
            for (int i = 0; i < 6; i++) {
                rowData[i] = incomingModel.getValueAt(selectedRow, i);
            }
            activeModel.addRow(rowData);
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
            Object[] rowData = new Object[6];
            for (int j = 0; j < 6; j++) {
                rowData[j] = incomingModel.getValueAt(i, j);
            }
            activeModel.addRow(rowData);
            incomingModel.removeRow(i);
        }
    }

    private void rejectAll() {
        incomingModel.setRowCount(0);
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Vehicle server started on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    executorService.execute(new ClientHandler(clientSocket));
                }
            } catch (IOException e) {
                System.out.println("Error starting vehicle server: " + e.getMessage());
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
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String vehicleData;
                while ((vehicleData = in.readLine()) != null) {
                    String[] vehicleAttributes = vehicleData.split(",");
                    SwingUtilities.invokeLater(() -> incomingModel.addRow(vehicleAttributes));
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

    public static void main(String[] args) {
        new manage_vehicle();
    }
}
