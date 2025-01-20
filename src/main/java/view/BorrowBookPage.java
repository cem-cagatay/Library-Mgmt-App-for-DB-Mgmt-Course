package view;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import domain.Book;

public class BorrowBookPage extends JFrame {

    public BorrowBookPage(Book book) {
        setTitle("Borrow Book - KUtüp Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/assets/logo2.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Motivation Label
        JLabel motivationLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>You are borrowing \"" + book.getTitle() + "\"!</h2>"
                + "<p>Choose the borrowing period to proceed.</p>"
                + "</div></html>", SwingConstants.CENTER);
        motivationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        motivationLabel.setForeground(Color.WHITE);
        motivationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(motivationLabel, BorderLayout.NORTH);

     // Input Fields Panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // space between components
        gbc.anchor = GridBagConstraints.WEST;  // center the components

        JLabel startDateLabel = new JLabel("Pick Start Date:");
        startDateLabel.setForeground(Color.WHITE);
        startDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Using JDateChooser for start date
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setDateFormatString("yyyy-MM-dd"); // Set the format you prefer

        // End Date label and field next to each other
        JLabel endDateLabel = new JLabel("Pick End Date:");
        endDateLabel.setForeground(Color.WHITE);
        endDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Using JDateChooser for end date
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd"); // Set the format you prefer

        // Positioning components in GridBagLayout
        gbc.gridx = 0;  // First column
        gbc.gridy = 0;  // First row
        inputPanel.add(startDateLabel, gbc);

        gbc.gridx = 1;  // Second column
        inputPanel.add(startDateChooser, gbc);

        gbc.gridx = 0;  // First column (next row)
        gbc.gridy = 1;  // Second row
        inputPanel.add(endDateLabel, gbc);

        gbc.gridx = 1;  // Second column (same row as the label)
        inputPanel.add(endDateChooser, gbc);

        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm Borrowing");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.addActionListener(e -> {
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();

            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter both start and end dates!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "<html><div style='text-align: center;'>"
                                + "<h2>Borrowing Confirmed!</h2>"
                                + "<p>You have borrowed \"" + book.getTitle()+ "\".</p>"
                                + "<p>From: " + startDate + " To: " + endDate + "</p>"
                                + "</div></html>",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose(); // Close the BorrowBookPage after confirmation
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
