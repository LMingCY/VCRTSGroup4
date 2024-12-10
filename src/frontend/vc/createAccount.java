package frontend.vc;

import backend.login.User;
import backend.master.Idgenerator;
import frontend.main.initialLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class createAccount extends JFrame{
    private JTextField usernameField, emailField, nameField;
    private JPasswordField passwordField;
    private JButton submitButton, backButton;
    private Idgenerator idgenerator;

    public createAccount() {
        setTitle("Create New User");
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

        addLabeledField(mainPanel, gbc, 0, "Username:", usernameField = new JTextField(), labelFont, fieldFont);
        addLabeledField(mainPanel, gbc, 1, "Name:", nameField = new JTextField(), labelFont, fieldFont);
        addLabeledField(mainPanel, gbc, 2, "E-Mail:", emailField = new JTextField(), labelFont, fieldFont);
        addLabeledField(mainPanel, gbc, 3, "Password:", passwordField = new JPasswordField(), labelFont, fieldFont);

        passwordField.setEchoChar('*');

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setFont(fieldFont);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show characters
            } else {
                passwordField.setEchoChar('*'); // Mask characters
            }
        });
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(showPasswordCheckbox, gbc);

        submitButton = new JButton("Back");
        submitButton.setFont(labelFont);
        submitButton.setBackground(buttonColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(this::back);
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(submitButton, gbc);

        backButton = new JButton("Submit");
        backButton.setFont(labelFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this::submitData);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField textField, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        textField.setFont(fieldFont);
        textField.setPreferredSize(new Dimension(200, 30));

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(textField, gbc);
    }
    private void submitData(ActionEvent e) {
        int userId = idgenerator.generateUserId();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText();
        String email = emailField.getText();
        User user = new User(userId,username,password,name,email);
        JOptionPane.showMessageDialog(this, "New user registered: " + username + "\n" + user.registerUser(userId,username,password,name,email));
        dispose();
        new initialLogin();
    }
    private void back(ActionEvent e) {
        new initialLogin();
        dispose();
    }

}
