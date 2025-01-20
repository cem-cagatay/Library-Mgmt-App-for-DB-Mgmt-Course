package view;

import javax.swing.*;

import domain.Book;

import java.awt.*;

public class BuyBookPage extends JFrame {

    public BuyBookPage(Book book) {
        setTitle("Buy Book - KUtüp Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // background Panel
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

        // motivation Label
        JLabel motivationLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>You're about to own \"" + book.getTitle() + "\"!</h2>"
                + "<p>Complete your purchase by entering your payment details.</p>"
                + "</div></html>", SwingConstants.CENTER);
        motivationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        motivationLabel.setForeground(Color.WHITE);
        motivationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(motivationLabel, BorderLayout.NORTH);

        // input fields panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // space between components
        gbc.anchor = GridBagConstraints.WEST;  // center the components

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel priceValueLabel = new JLabel("$10.00");
        priceValueLabel.setForeground(Color.WHITE);
        priceValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Credit Card label and field next to each other
        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        creditCardLabel.setForeground(Color.WHITE);
        creditCardLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField creditCardField = new JTextField(10); // Reduced size

        // positioning components in GridBagLayout
        gbc.gridx = 0;  // first column
        gbc.gridy = 0;  // first row
        inputPanel.add(priceLabel, gbc);

        gbc.gridx = 1;  // second column
        inputPanel.add(priceValueLabel, gbc);

        gbc.gridx = 0;  // first column (next row)
        gbc.gridy = 1;  // second row
        inputPanel.add(creditCardLabel, gbc);

        gbc.gridx = 1;  // second column (same row as the label)
        inputPanel.add(creditCardField, gbc);

        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        // button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.addActionListener(e -> {
            String creditCardNumber = creditCardField.getText().trim();

            if (creditCardNumber.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your credit card number!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "<html><div style='text-align: center;'>"
                                + "<h2>Congratulations!</h2>"
                                + "<p>You've successfully purchased \"" + book.getTitle()+ "\".</p>"
                                + "<p>Enjoy your journey through its pages!</p>"
                                + "</div></html>",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose(); // close the BuyBookPage after confirmation
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
