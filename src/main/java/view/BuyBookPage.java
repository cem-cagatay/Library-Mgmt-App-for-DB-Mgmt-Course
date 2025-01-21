package view;

import javax.swing.*;

import database.DatabaseHandler;

import java.awt.*;
import domain.BookCopy;
import domain.Member;
import domain.Purchase;

public class BuyBookPage extends JFrame {

    public BuyBookPage(BookCopy bookCopy, Member member) {
        setTitle("Buy Book - Kütüp Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background panel
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
                + "<h2>You're about to own Copy ID: " + bookCopy.getCopyId() + "!</h2>"
                + "<p>Complete your purchase by entering your payment details.</p>"
                + "</div></html>", SwingConstants.CENTER);
        motivationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        motivationLabel.setForeground(Color.WHITE);
        motivationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(motivationLabel, BorderLayout.NORTH);

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Price Label
        JLabel priceLabel = new JLabel("Price: $" + bookCopy.getPrice());
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Credit Card Number
        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        creditCardLabel.setForeground(Color.WHITE);
        creditCardLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField creditCardField = new JTextField(15);

        // Billing Address
        JLabel billingAddressLabel = new JLabel("Billing Address:");
        billingAddressLabel.setForeground(Color.WHITE);
        billingAddressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField billingAddressField = new JTextField(20);

        // Add components to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(priceLabel, gbc);

        gbc.gridy++;
        inputPanel.add(creditCardLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(creditCardField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(billingAddressLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(billingAddressField, gbc);

        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.addActionListener(e -> {
            String creditCardNumber = creditCardField.getText().trim();
            String billingAddress = billingAddressField.getText().trim();

            if (creditCardNumber.isEmpty() || billingAddress.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                // update Member object with entered details
                member.setBillingAddress(billingAddress);
                member.setCreditCardNumber(creditCardNumber);

                // save updates and purchase to the database
                boolean memberUpdated = DatabaseHandler.updateMemberDetails(member);
                boolean purchaseSaved = DatabaseHandler.savePurchaseToDatabase(new Purchase(member, bookCopy));
                boolean statusUpdated = DatabaseHandler.updateBookCopyStatus(bookCopy.getCopyId(), "Unavailable");

                if (memberUpdated && purchaseSaved && statusUpdated) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Purchase successful! Enjoy your new book.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error occurred during purchase. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
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