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
        selectUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new user();
            }
        });

        selectOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setVisible(false);
                new owner();
            }
        });
    }
    private static void setButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectOwner);
        buttonPanel.add(selectUser);
    }
    private static void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcomeMessage, userSelectMessage; //This declares two labels
        welcomeMessage = new JLabel("Welcome to VCRTS!");
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        userSelectMessage = new JLabel("I'm a");
        userSelectMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeMessage);
        mainPanel.add(userSelectMessage);
        mainPanel.add(buttonPanel);
    }
    public static void setMainFrame() {
        mainFrame = new JFrame("VCRTS Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
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
    public static void main(String[] args) {
        getMainFrame();
    }
}