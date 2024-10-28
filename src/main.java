import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    private static JFrame mainFrame;
    private static JPanel mainPanel, buttonPanel;
    private static JButton selectUser, selectOwner;

    private static void setButtons() {
        selectUser = new JButton("User");
        selectOwner = new JButton("Owner");

        selectUser.setBackground(new Color(100, 150, 250));
        selectUser.setForeground(Color.WHITE);
        selectUser.setPreferredSize(new Dimension(80,30));
        selectOwner.setBackground(new Color(100, 150, 250));
        selectOwner.setForeground(Color.WHITE);
        selectOwner.setPreferredSize(new Dimension(80,30));

        
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        selectUser.setFont(buttonFont);
        selectOwner.setFont(buttonFont);

        selectUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new user(); // Assuming User is another class
            }
        });

        selectOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new owner(); // Assuming Owner is another class
            }
        });
    }

    private static void setButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); // Set background color
        buttonPanel.add(selectOwner);
        buttonPanel.add(selectUser);
    }

    private static void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        JLabel welcomeMessage, userSelectMessage; 
        welcomeMessage = new JLabel("Welcome to VCRTS!");
        userSelectMessage = new JLabel("I'm a");

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        welcomeMessage.setFont(labelFont);
        userSelectMessage.setFont(new Font("Arial", Font.PLAIN, 16));

        // Center alignment for labels
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        userSelectMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20)); // Add vertical space
        mainPanel.add(welcomeMessage);
        mainPanel.add(userSelectMessage);
        mainPanel.add(Box.createVerticalStrut(10)); // Add vertical space
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add vertical space
    }

    public static void setMainFrame() {
        mainFrame = new JFrame("VCRTS Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300); // Increased size for better layout
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(true);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
    }

    public static void getMainFrame() {
        setButtons();
        setButtonPanel();
        setMainPanel();
        setMainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //MAC Support
        getMainFrame();
    }
}
