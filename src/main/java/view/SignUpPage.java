package view;

import javax.swing.*;
import java.awt.*;
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
        emailLabel.setForeground(Color.WHITE); // Set text color to white
        emailLabel.setBounds(300, 250, 100, 30);

        JTextField emailField = new JTextField();
        emailField.setBounds(400, 250, 150, 30);
        emailField.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        passwordLabel.setBounds(300, 300, 100, 30);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 300, 150, 30);
        passwordField.setBackground(Color.WHITE);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE); // Set text color to white
        confirmPasswordLabel.setBounds(250, 350, 150, 30);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(400, 350, 150, 30);
        confirmPasswordField.setBackground(Color.WHITE);

        // sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(375, 400, 100, 30);
        signUpButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

         // Check if email is empty
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if email is valid
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // check if passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // checking password validity
            if (!PasswordValidator.isValidPassword(password)) {
                JOptionPane.showMessageDialog(this,
                        "Password must be at least 8 characters long, include uppercase, lowercase, digit, and special character.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Sign Up Successful!");
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
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(confirmPasswordLabel);
        backgroundPanel.add(confirmPasswordField);
        backgroundPanel.add(signUpButton);
        backgroundPanel.add(backButton);

        setLocationRelativeTo(null); // center the frame on the screen
        setVisible(true);
    }
    
 // Email validation method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
