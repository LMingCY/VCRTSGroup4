package frontend.vc;

import backend.MySQL.Driver;
import backend.login.User;
import backend.vehicle.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
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
    Driver db = new Driver();

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
        backButton.addActionListener(e -> backToMenu());

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
        private int vehicleId, ownerId, vehicleStatus;
        private Duration vehicleResidencyTime;
        private String vehicleMake, vehicleModel, vehicleCurrentJob;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

                String vehicleData = in.readUTF();
                String[] vehicleParts = vehicleData.split(",");
                vehicleId = Integer.parseInt(vehicleParts[0]);
                vehicleMake = vehicleParts[1];
                vehicleModel = vehicleParts[2];
                ownerId = Integer.parseInt(vehicleParts[3]);
                vehicleStatus = Integer.parseInt(vehicleParts[4]);
                vehicleResidencyTime = Duration.ofMinutes(Integer.parseInt(vehicleParts[5]));
                vehicleCurrentJob = vehicleParts[6];
                Vehicle vehicle = new Vehicle(vehicleId, vehicleMake, vehicleModel, ownerId, vehicleStatus, vehicleResidencyTime, vehicleCurrentJob);

                SwingUtilities.invokeLater(() -> incomingModel.addRow(vehicleParts));
                //300000007,BAE,Trident,101,0,600,,,2024-11-19T22:44:10.312618800

                while (true) {
                    Thread.sleep(100);
                    if (isJobAccepted(String.valueOf(vehicleId))) {
                        out.writeUTF("Accepted");
                        writeVehicleToFile(vehicle, "owner_transaction_accepted_by_vc");
                        System.out.println(vehicle);
                        db.addVehicle(vehicle);
                        break;
                    } else if (isVehicleRejected(String.valueOf(vehicleId))) {
                        out.writeUTF("Rejected");
                        break;
                    }
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
        }

        private boolean isJobAccepted(String vehicleId) {
            return findRowIndex(activeModel, vehicleId) != -1;
        }

        private boolean isVehicleRejected(String vehicleId) {
            return findRowIndex(incomingModel, vehicleId) == -1 && findRowIndex(activeModel, vehicleId) == -1;
        }

        private int findRowIndex(DefaultTableModel model, String vehicleId) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(vehicleId)) {
                    return i;
                }
            }
            return -1;
        }
    }
    public void writeVehicleToFile(Vehicle vehicle, String filePath) {
        String timestamp = LocalDateTime.now().toString();
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(vehicle.toString() + "," + timestamp + "\n");
        } catch (IOException e) {
            System.out.println("Error writing backend.vehicle information to file: " + e.getMessage());
        }
    }
    private void backToMenu() {
        dispose();
    }
}
