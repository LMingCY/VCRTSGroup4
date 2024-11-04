import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vc extends JFrame {
    JLabel titleLabel;
    JButton inspectJobButton, jobCompletionTimeButton, backButton;

    public vc() {
        super("Vehicular Cloud RTS Controller");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        Color buttonColor = new Color(100, 150, 250);
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        titleLabel = new JLabel("Vehicular Cloud RTS - Controller");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        inspectJobButton = new JButton("Inspect Job");
        inspectJobButton.setBackground(buttonColor);
        inspectJobButton.setForeground(Color.WHITE);
        inspectJobButton.setFont(labelFont);

        jobCompletionTimeButton = new JButton("Job Completion Time");
        jobCompletionTimeButton.setBackground(buttonColor);
        jobCompletionTimeButton.setForeground(Color.WHITE);
        jobCompletionTimeButton.setFont(labelFont);

        backButton = new JButton("Back");
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(labelFont);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(inspectJobButton, gbc);

        gbc.gridy++;
        panel.add(jobCompletionTimeButton, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        add(panel);
        setVisible(true);

        inspectJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Inspecting job details...");
                // Code to handle job inspection would go here
            }
        });

        jobCompletionTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Calculating job completion time...");
                // Code to calculate job completion time would go here
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.getMainFrame();
                dispose();
            }
        });
    }
}
