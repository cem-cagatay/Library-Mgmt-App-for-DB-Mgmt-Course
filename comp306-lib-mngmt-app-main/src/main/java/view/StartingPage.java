package view;

import javax.swing.*;
import java.awt.*;

public class StartingPage extends JFrame {
    public StartingPage() {
        setTitle("LibraLink - Library Management System");
        setSize(800, 600); // Frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Load and scale the background image
        String imagePath = "/Users/OzzY/Desktop/comp306-lib-mngmt-app-main/src/main/java/images/logo.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the background label
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        // Create buttons for navigation
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // Set button sizes and positions
        int buttonWidth = 120;
        int buttonHeight = 40;
        int spacing = 20; // Space between buttons

        // Calculate positions for horizontal alignment
        int totalWidth = buttonWidth * 2 + spacing;
        int xPosition = (800 - totalWidth) / 2; // Center the buttons horizontally
        int yPosition = 520; // Place them at the bottom

        loginButton.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
        signUpButton.setBounds(xPosition + buttonWidth + spacing, yPosition, buttonWidth, buttonHeight);

        // Add action listeners for buttons
        loginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        signUpButton.addActionListener(e -> {
            new SignUpPage().setVisible(true);
            dispose();
        });

        // Add buttons to the background label
        backgroundLabel.setLayout(null);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(signUpButton);

        // Set the background label as the content pane
        setContentPane(backgroundLabel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StartingPage();
    }
}
