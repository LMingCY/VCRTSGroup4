package frontend.owner;

import backend.dashboard.OwnerDashboard;
import backend.master.NumericTextField;
import backend.vehicle.Vehicle;
import frontend.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;


public class owner_dash extends JFrame {
 private JTextField vehicleInfoField;
 private NumericTextField ownerIDField, residencyTimeField;
 private JButton submitButton, clearButton, signOutButton, helpButton;
 private OwnerDashboard ownerDashboard = new OwnerDashboard();
 private Vehicle vehicle;


    // Constructor to initialize and configure the GUI
 public owner_dash() {
     setTitle("Vehicular Cloud Console");
     setSize(400, 400); // Set window size
     setDefaultCloseOperation(EXIT_ON_CLOSE); 
     setLocationRelativeTo(null); 

     // Main panel using GridBagLayout for flexible layout and positioning
     JPanel mainPanel = new JPanel(new GridBagLayout());
     mainPanel.setBackground(new Color(240, 240, 240)); // Set light gray background color
     mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around panel contents

     // Define GridBagConstraints for component alignment and spacing
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(10, 10, 10, 10); 
     gbc.fill = GridBagConstraints.HORIZONTAL; 

     // Define fonts and button color
     Font labelFont = new Font("Arial", Font.BOLD, 14);
     Font fieldFont = new Font("Arial", Font.PLAIN, 14);
     Color buttonColor = new Color(100, 150, 250); 

     // Create and configure labels and text fields for owner data input
     JLabel ownerIDLabel = new JLabel("Owner ID:");
     ownerIDLabel.setFont(labelFont);
     ownerIDField = new NumericTextField(9);
     ownerIDField.setFont(fieldFont);

     JLabel vehicleInfoLabel = new JLabel("Vehicle Make:");
     vehicleInfoLabel.setFont(labelFont);
     vehicleInfoField = new JTextField();
     vehicleInfoField.setFont(fieldFont);

     JLabel residencyTimeLabel = new JLabel("Residency Time:");
     residencyTimeLabel.setFont(labelFont);
     residencyTimeField = new NumericTextField(10);
     residencyTimeField.setFont(fieldFont);

     // Configure the Submit button, add styling, and set its action
     submitButton = new JButton("Submit");
     submitButton.setFont(labelFont);
     submitButton.setBackground(buttonColor);
     submitButton.setForeground(Color.WHITE);
     submitButton.setFocusPainted(false);
     submitButton.addActionListener(this::submitData); 

     // Configure the Clear button and set its action to clear fields
     clearButton = new JButton("Clear");
     clearButton.setFont(labelFont);
     clearButton.setBackground(buttonColor);
     clearButton.setForeground(Color.WHITE);
     clearButton.setFocusPainted(false);
     clearButton.addActionListener(e -> clearFields()); // Clear fields when clicked

     // Configure the Sign Out button to return to main menu
     signOutButton = new JButton("Sign Out");
     signOutButton.setFont(labelFont);
     signOutButton.setBackground(buttonColor);
     signOutButton.setForeground(Color.WHITE);
     signOutButton.setFocusPainted(false);
     signOutButton.addActionListener(e -> signOut()); // Return to main frame when clicked

     // Configure the Help button and set its action to display help information
     helpButton = new JButton("Help");
     helpButton.setFont(labelFont);
     helpButton.setBackground(buttonColor);
     helpButton.setForeground(Color.WHITE);
     helpButton.setFocusPainted(false);
     helpButton.addActionListener(e -> showHelp()); // Show help dialog when clicked

     // Add components to the layout, configuring their position using GridBagConstraints
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
     mainPanel.add(helpButton, gbc); // Add help button centered in layout

     // Add the main panel to the frame and make it visible
     add(mainPanel);
     setVisible(true);
 }

 // Method to handle Submit button action - captures input data and saves to file
 private void submitData(ActionEvent e) {
     // Combine input data with a timestamp
     vehicle = ownerDashboard.addVehicle(vehicleInfoField.getText(), vehicleInfoField.getText(), Integer.parseInt(ownerIDField.getText()), Duration.ofMinutes(Integer.parseInt(residencyTimeField.getText()))); //need to come up a way to store id as int.
     ownerDashboard.writeVehicleToFile(vehicle, "owner_transaction.txt");
     JOptionPane.showMessageDialog(this, "Entries saved!"); // Show confirmation dialog
 }

 // Method to clear all text fields
 private void clearFields() {
     ownerIDField.setText("");
     vehicleInfoField.setText("");
     residencyTimeField.setText("");
 }

 // Method to handle Sign Out button action - returns to main frame
 private void signOut() {
     main.getMainFrame(); // Show main frame again
     dispose(); // Close this window
 }

 // Method to show help information in a dialog
 private void showHelp() {
     JOptionPane.showMessageDialog(this, "Enter your details and click Submit."); // Display help message
 }

 // Method to save data to a file
 private void saveToFile(String data) {
     try (FileWriter writer = new FileWriter("owner_transaction.txt", true)) { // Open file in append mode
         writer.write(data + "\n\n"); // Write data to file
     } catch (IOException ex) { // Handle file I/O errors
         JOptionPane.showMessageDialog(this, "Error saving data."); // Show error message
     }
 }
}