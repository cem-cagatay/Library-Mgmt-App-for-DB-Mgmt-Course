package view;

import javax.swing.*;

import database.DatabaseConnection;
import database.DatabaseHandler;
import domain.Member;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginPage extends JFrame {
	
    JTextField emailField;
    JPasswordField passwordField;
	
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
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(300, 250, 100, 30);

        emailField = new JTextField();
        emailField.setBounds(400, 250, 150, 30); 
        emailField.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); 
        passwordLabel.setBounds(300, 300, 100, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(400, 300, 150, 30); 
        passwordField.setBackground(Color.WHITE);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(400, 350, 100, 30); // centering the fields
        loginButton.addActionListener(e -> {
            try {
                handleLogin();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30); // bottom left corner
        backButton.addActionListener(e -> {
            new StartingPage().setVisible(true);
            dispose();
        });
        
     // Admin button
        JButton adminButton = new JButton("Admin");
        adminButton.setBounds(650, 500, 100, 30); // bottom right corner
        adminButton.addActionListener(e -> {
            Member adminMember = new Member(0, "Admin", "User", "admin@kutup.com");
            new MainPage(adminMember).setVisible(true);
            dispose();
        });

        // add components to the background label        
        backgroundPanel.add(emailLabel);
        backgroundPanel.add(emailField);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(loginButton);
        backgroundPanel.add(backButton);
        backgroundPanel.add(adminButton);

        setLocationRelativeTo(null); // center the frame on the screen
        setVisible(true);
    }

    private void handleLogin() throws SQLException {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = DatabaseHandler.login(email, password);
        if (member != null) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            EventQueue.invokeLater(() -> {
                MainPage mainPageFrame = new MainPage(member);
                mainPageFrame.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }
}
