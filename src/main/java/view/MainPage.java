package view;

import javax.swing.*;

import domain.Member;

import java.awt.*;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

    public MainPage(Member member) {
        setTitle("Main Page - KU Suna Kıraç Library");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background panel
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

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to KU Suna Kıraç Library System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(200, 30, 400, 30);
        backgroundPanel.add(welcomeLabel);

        // Display Member's Name
        JLabel memberNameLabel = new JLabel("Logged in as: " + member.getName() + " " + member.getLastName());
        memberNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        memberNameLabel.setForeground(Color.WHITE);
        memberNameLabel.setBounds(10, 10, 300, 20); // Displayed at the top-left corner
        backgroundPanel.add(memberNameLabel);

        // Operations Panel
        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        operationsPanel.setBounds(150, 150, 500, 300);
        operationsPanel.setOpaque(false);

        // Buttons for operations
        JButton manageBooksButton = new JButton("Manage Books");
        JButton manageAuthorsButton = new JButton("Manage Authors");
        JButton manageMembersButton = new JButton("Manage Members");
        JButton borrowBookButton = new JButton("Borrow a Book");
        JButton returnBookButton = new JButton("Return a Book");
        JButton logoutButton = new JButton("Logout");

        // Add Action Listeners
        manageBooksButton.addActionListener(e -> showMessage("Manage Books"));
        manageAuthorsButton.addActionListener(e -> showMessage("Manage Authors"));
        manageMembersButton.addActionListener(e -> showMessage("Manage Members"));
        borrowBookButton.addActionListener(e -> showMessage("Borrow a Book"));
        returnBookButton.addActionListener(e -> showMessage("Return a Book"));
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            new LoginPage().setVisible(true); // Navigate back to login page
            dispose();
        });

        // Add buttons to the panel
        operationsPanel.add(manageBooksButton);
        operationsPanel.add(manageAuthorsButton);
        operationsPanel.add(manageMembersButton);
        operationsPanel.add(borrowBookButton);
        operationsPanel.add(returnBookButton);
        operationsPanel.add(logoutButton);

        // Add components to the background panel
        backgroundPanel.add(operationsPanel);

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void showMessage(String operation) {
        JOptionPane.showMessageDialog(this, operation + " is currently under development.", "Feature Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
