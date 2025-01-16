package view;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login - KU Suna Kıraç Library");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
     // Background panel with custom painting
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/assets/logo2.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // username and password fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(300, 250, 100, 30);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(400, 250, 150, 30); 
        usernameField.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); 
        passwordLabel.setBounds(300, 300, 100, 30);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 300, 150, 30); 
        passwordField.setBackground(Color.WHITE);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(375, 350, 100, 30); // centering the fields
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // validation
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!PasswordValidator.isValidPassword(password)) {
                JOptionPane.showMessageDialog(this,
                        "Password must be at least 8 characters long, include uppercase, lowercase, digit, and special character.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: Add actual authentication logic
            JOptionPane.showMessageDialog(this, "Login Successful!");
        });

        // back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30); // bottom left corner
        backButton.addActionListener(e -> {
            new StartingPage().setVisible(true);
            dispose();
        });

        // add components to the background label        
        backgroundPanel.add(usernameLabel);
        backgroundPanel.add(usernameField);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(loginButton);
        backgroundPanel.add(backButton);

        setLocationRelativeTo(null); // center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
