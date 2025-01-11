package view;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JFrame {
    public SignUpPage() {
        setTitle("Sign Up - LibraLink");
        setSize(800, 600); // Frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Load and scale the background image
        String imagePath = "/Users/OzzY/Desktop/comp306-lib-mngmt-app-main/src/main/java/images/logo3.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the background label
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 800, 600); // Match frame size
        add(backgroundLabel);

        // Form elements
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Set text color to white
        usernameLabel.setBounds(300, 250, 100, 30);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(400, 250, 150, 30);
        usernameField.setBackground(Color.WHITE);

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

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(375, 400, 100, 30);
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if username is empty
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check password validity
            if (!PasswordValidator.isValidPassword(password)) {
                JOptionPane.showMessageDialog(this,
                        "Password must be at least 8 characters long, include uppercase, lowercase, digit, and special character.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Sign Up Successful!");
        });

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        backButton.addActionListener(e -> {
            new StartingPage().setVisible(true);
            dispose();
        });

        // Add components to the background label
        backgroundLabel.setLayout(null); // Allow absolute positioning
        backgroundLabel.add(usernameLabel);
        backgroundLabel.add(usernameField);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(confirmPasswordLabel);
        backgroundLabel.add(confirmPasswordField);
        backgroundLabel.add(signUpButton);
        backgroundLabel.add(backButton);

        // Set the background label as the content pane
        setContentPane(backgroundLabel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
