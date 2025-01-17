package view;

import javax.swing.*;

import database.DatabaseHandler;

import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignUpPage extends JFrame {
    public SignUpPage() {
        setTitle("Sign Up - KU Suna Kıraç Library");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
     // Background panel with custom painting
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/assets/logo3.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

     // Form elements
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); 
        emailLabel.setBounds(300, 200, 100, 30);

        JTextField emailField = new JTextField();
        emailField.setBounds(400, 200, 150, 30);
        emailField.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(300, 250, 100, 30);

        JTextField nameField = new JTextField();
        nameField.setBounds(400, 250, 150, 30);
        nameField.setBackground(Color.WHITE);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(300, 300, 100, 30);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(400, 300, 150, 30);
        lastNameField.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(300, 350, 100, 30);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 350, 150, 30);
        passwordField.setBackground(Color.WHITE);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setBounds(250, 400, 150, 30);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(400, 400, 150, 30);
        confirmPasswordField.setBackground(Color.WHITE);

        // sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(400, 450, 100, 30);
        signUpButton.addActionListener(e -> {
        	try {
                signUp(nameField, lastNameField, emailField, passwordField, confirmPasswordField);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error during registration: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        // back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        backButton.addActionListener(e -> {
            new StartingPage().setVisible(true);
            dispose();
        });

        // add components to the background label
        backgroundPanel.add(emailLabel);
        backgroundPanel.add(emailField);
        backgroundPanel.add(nameLabel);
        backgroundPanel.add(nameField);
        backgroundPanel.add(lastNameLabel);
        backgroundPanel.add(lastNameField);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(confirmPasswordLabel);
        backgroundPanel.add(confirmPasswordField);
        backgroundPanel.add(signUpButton);
        backgroundPanel.add(backButton);

        setLocationRelativeTo(null); // center the frame on the screen
        setVisible(true);
    }
    
    private void signUp(JTextField nameField, JTextField lastNameField, JTextField emailField, JPasswordField passwordField, JPasswordField confirmPasswordField) throws SQLException {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate input
        if (email.isEmpty() || name.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!PasswordValidator.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 8 characters long, include uppercase, lowercase, digit, and special character.",
                    "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // call the DatabaseHandler to insert the member
        boolean isSuccess = DatabaseHandler.insertMember(name, lastName, email, password);
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Sign Up Successful!");
            dispose();
            new LoginPage().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 // Email validation method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

}
