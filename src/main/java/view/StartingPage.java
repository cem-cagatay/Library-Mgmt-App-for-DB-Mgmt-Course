package view;

import javax.swing.*;
import java.awt.*;

public class StartingPage extends JFrame {
    public StartingPage() {
        setTitle("KUtüp Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Background panel with custom painting
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/assets/new_logo.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // button sizes and positions
        int buttonWidth = 120;
        int buttonHeight = 40;
        int spacing = 20;

        // positions for horizontal alignment
        int totalWidth = buttonWidth * 2 + spacing;
        int xPosition = (800 - totalWidth) / 2; // center the buttons horizontally
        int yPosition = 520; // at the bottom

        loginButton.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
        signUpButton.setBounds(xPosition + buttonWidth + spacing, yPosition, buttonWidth, buttonHeight);

        // action listeners for buttons
        loginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        signUpButton.addActionListener(e -> {
            new SignUpPage().setVisible(true);
            dispose();
        });

        // add buttons to the background label
        backgroundPanel.add(loginButton);
        backgroundPanel.add(signUpButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StartingPage();
    }
}
