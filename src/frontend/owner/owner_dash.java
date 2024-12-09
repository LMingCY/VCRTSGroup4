package frontend.owner;

import backend.dashboard.OwnerDashboard;
import backend.login.User;
import backend.master.NumericTextField;
import backend.vehicle.Vehicle;
import frontend.main.userMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.Duration;
import java.net.Socket;


public class owner_dash extends JFrame {
 private JTextField vehicleInfoField, vehicleModelField;
 private NumericTextField ownerIDField, residencyTimeField;
 private JButton submitButton, clearButton, signOutButton, helpButton;
 private OwnerDashboard ownerDashboard = new OwnerDashboard();
 private Vehicle vehicle;
 private User user;


 public owner_dash(User user) {
     this.user = user;
     setTitle("Vehicular Cloud Console");
     setSize(400, 400);
     setDefaultCloseOperation(EXIT_ON_CLOSE); 
     setLocationRelativeTo(null); 

     JPanel mainPanel = new JPanel(new GridBagLayout());
     mainPanel.setBackground(new Color(240, 240, 240));
     mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(10, 10, 10, 10); 
     gbc.fill = GridBagConstraints.HORIZONTAL; 

     Font labelFont = new Font("Arial", Font.BOLD, 14);
     Font fieldFont = new Font("Arial", Font.PLAIN, 14);
     Color buttonColor = new Color(100, 150, 250); 

     JLabel ownerIDLabel = new JLabel("Owner ID:");
     ownerIDLabel.setFont(labelFont);
     ownerIDField = new NumericTextField(9);
     ownerIDField.setFont(fieldFont);

     JLabel vehicleInfoLabel = new JLabel("Vehicle Make:");
     vehicleInfoLabel.setFont(labelFont);
     vehicleInfoField = new JTextField();
     vehicleInfoField.setFont(fieldFont);

     JLabel vehicleModelLabel = new JLabel("Vehicle Model:");
     vehicleModelLabel.setFont(labelFont);
     vehicleModelField = new JTextField();
     vehicleModelField.setFont(fieldFont);

     JLabel residencyTimeLabel = new JLabel("Residency Time:");
     residencyTimeLabel.setFont(labelFont);
     residencyTimeField = new NumericTextField(10);
     residencyTimeField.setFont(fieldFont);

     submitButton = new JButton("Submit");
     submitButton.setFont(labelFont);
     submitButton.setBackground(buttonColor);
     submitButton.setForeground(Color.WHITE);
     submitButton.setFocusPainted(false);
     submitButton.addActionListener(this::submitData); 

     clearButton = new JButton("Clear");
     clearButton.setFont(labelFont);
     clearButton.setBackground(buttonColor);
     clearButton.setForeground(Color.WHITE);
     clearButton.setFocusPainted(false);
     clearButton.addActionListener(e -> clearFields());

     signOutButton = new JButton("Back");
     signOutButton.setFont(labelFont);
     signOutButton.setBackground(buttonColor);
     signOutButton.setForeground(Color.WHITE);
     signOutButton.setFocusPainted(false);
     signOutButton.addActionListener(e -> {
         try {
             signOut();
         } catch (UnsupportedLookAndFeelException ex) {
             throw new RuntimeException(ex);
         } catch (ClassNotFoundException ex) {
             throw new RuntimeException(ex);
         } catch (InstantiationException ex) {
             throw new RuntimeException(ex);
         } catch (IllegalAccessException ex) {
             throw new RuntimeException(ex);
         }
     });

     helpButton = new JButton("Help");
     helpButton.setFont(labelFont);
     helpButton.setBackground(buttonColor);
     helpButton.setForeground(Color.WHITE);
     helpButton.setFocusPainted(false);
     helpButton.addActionListener(e -> showHelp());

     gbc.gridx = 0; gbc.gridy = 0;
     mainPanel.add(ownerIDLabel, gbc); 
     gbc.gridx = 1;
     mainPanel.add(ownerIDField, gbc); 

     gbc.gridy++;
     gbc.gridx = 0;
     mainPanel.add(vehicleInfoLabel, gbc); 
     gbc.gridx = 1;
     mainPanel.add(vehicleInfoField, gbc); 

     gbc.gridy++;
     gbc.gridx = 0;
     mainPanel.add(vehicleModelLabel, gbc); 
     gbc.gridx = 1;
     mainPanel.add(vehicleModelField, gbc); 

     gbc.gridy++;
     gbc.gridx = 0;
     mainPanel.add(residencyTimeLabel, gbc); 
     gbc.gridx = 1;
     mainPanel.add(residencyTimeField, gbc); 

     gbc.gridy++;
     gbc.gridx = 0; gbc.gridwidth = 2;
     gbc.anchor = GridBagConstraints.CENTER; // Center the submit button
     mainPanel.add(submitButton, gbc);

     gbc.gridy++;
     gbc.gridwidth = 1;
     mainPanel.add(clearButton, gbc); 
     gbc.gridx = 1;
     mainPanel.add(signOutButton, gbc); 

     gbc.gridy++;
     gbc.gridx = 0; gbc.gridwidth = 2;
     mainPanel.add(helpButton, gbc);

     add(mainPanel);
     setVisible(true);
 }

 private void submitData(ActionEvent e) {
     vehicle = ownerDashboard.addVehicle(vehicleInfoField.getText(), vehicleModelField.getText(), Integer.parseInt(ownerIDField.getText()), Duration.ofMinutes(Integer.parseInt(residencyTimeField.getText())));
     ownerDashboard.writeVehicleToFile(vehicle, "owner_transaction.txt");
     try (Socket socket = new Socket("localhost", 25566);
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          DataInputStream in = new DataInputStream(socket.getInputStream())) {

         out.writeUTF(vehicle.toString());

         JOptionPane.showMessageDialog(this, "Vehicle sent to server! Waiting for Controller...");
         ownerIDField.setEnabled(false);
         vehicleModelField.setEnabled(false);
         vehicleInfoField.setEnabled(false);
         residencyTimeField.setEnabled(false);
         submitButton.setEnabled(false);

         String response = in.readUTF();
         JOptionPane.showMessageDialog(this, "Admin Response: " + response);

         if (response.equalsIgnoreCase("Rejected")) {
             ownerIDField.setEnabled(true);
             vehicleModelField.setEnabled(true);
             vehicleInfoField.setEnabled(true);
             residencyTimeField.setEnabled(true);
             submitButton.setEnabled(true);
         } else {
             ownerIDField.setText("");
             vehicleModelField.setText("");
             vehicleInfoField.setText("");
             residencyTimeField.setText("");
             ownerIDField.setEnabled(true);
             vehicleModelField.setEnabled(true);
             vehicleInfoField.setEnabled(true);
             residencyTimeField.setEnabled(true);
             submitButton.setEnabled(true);
             ownerDashboard.writeVehicleToFile(vehicle, "owner_transaction_approved.txt");
         }

     } catch (IOException ex) {
         JOptionPane.showMessageDialog(this, "Error connecting to server.");
     }
 }

 private void clearFields() {
     ownerIDField.setText("");
     vehicleInfoField.setText("");
     vehicleModelField.setText("");
     residencyTimeField.setText("");
 }

 private void signOut() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
     new userMain(user);
     dispose();
 }

 private void showHelp() {
     JOptionPane.showMessageDialog(this, "Enter your details and click Submit.");
 }

}