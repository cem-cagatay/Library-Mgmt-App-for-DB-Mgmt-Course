package view;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login - LibraLink");
        setSize(800, 600); // Frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Load and scale the background image
        String imagePath = "/Users/OzzY/Desktop/comp306-lib-mngmt-app-main/src/main/java/images/logo2.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the background label
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 800, 600); // Match frame size
        add(backgroundLabel);

        // Create username and password fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Set text color to white
        usernameLabel.setBounds(300, 250, 100, 30); // Position username label

        JTextField usernameField = new JTextField();
        usernameField.setBounds(400, 250, 150, 30); // Position username field
        usernameField.setBackground(Color.WHITE); // Set background to white

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        passwordLabel.setBounds(300, 300, 100, 30); // Position password label

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 300, 150, 30); // Position password field
        passwordField.setBackground(Color.WHITE); // Set background to white

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(375, 350, 100, 30); // Centered below fields
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Basic validation
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

        // Create back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30); // Bottom left corner
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
        backgroundLabel.add(loginButton);
        backgroundLabel.add(backButton);

        // Set the background label as the content pane
        setContentPane(backgroundLabel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
