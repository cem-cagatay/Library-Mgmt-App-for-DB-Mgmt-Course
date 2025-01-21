package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseHandler;
import domain.BookCopy;
import domain.Member;


public class AvailableCopiesPage extends JFrame {

    public AvailableCopiesPage(List<BookCopy> availableCopies, Member member) {
        setTitle("Available Copies - Kütüp Library Management System");
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

        // Title Label
        JLabel titleLabel = new JLabel("Available Copies", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Book Copies Panel (Scrollable)
        JPanel copiesPanel = new JPanel();
        copiesPanel.setLayout(new BoxLayout(copiesPanel, BoxLayout.Y_AXIS));
        copiesPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(copiesPanel);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Map to associate JRadioButton with BookCopy
        Map<JRadioButton, BookCopy> buttonToBookCopyMap = new HashMap<>();
        ButtonGroup buttonGroup = new ButtonGroup();

        for (BookCopy copy : availableCopies) {
            JRadioButton copyButton = new JRadioButton(
                String.format("Copy ID: %d | Price: %.2f | Location: %d-%c%d",
                    copy.getCopyId(), copy.getPrice(), copy.getFloorNumber(),
                    copy.getShelfLetter(), copy.getShelfNumber()));
            copyButton.setFont(new Font("Arial", Font.BOLD, 14));
            copyButton.setOpaque(false);
            copyButton.setForeground(Color.WHITE);
            buttonGroup.add(copyButton);
            copiesPanel.add(copyButton);

            // Associate the button with the corresponding BookCopy
            buttonToBookCopyMap.put(copyButton, copy);
        }

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyButton.setBackground(Color.WHITE);
        buyButton.addActionListener(e -> {
            // find the selected button and retrieve the associated BookCopy
            JRadioButton selectedButton = null;
            for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedButton = (JRadioButton) button; // Cast to JRadioButton
                    break;
                }
            }

            if (selectedButton == null) {
                JOptionPane.showMessageDialog(this, "Please select a copy to buy.");
            } else {
                BookCopy selectedCopy = buttonToBookCopyMap.get(selectedButton);
                new BuyBookPage(selectedCopy, member).setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(buyButton);

        JButton borrowButton = new JButton("Borrow");
        borrowButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowButton.setBackground(Color.WHITE);
        
        borrowButton.addActionListener(e -> {
            // find the selected button and retrieve the associated BookCopy
            JRadioButton selectedButton = null;
            for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedButton = (JRadioButton) button;
                    break;
                }
            }

            if (selectedButton == null) {
                JOptionPane.showMessageDialog(this, "Please select a copy to borrow.");
            } else {
                BookCopy selectedCopy = buttonToBookCopyMap.get(selectedButton);
                new BorrowBookPage(selectedCopy, member).setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(borrowButton);
        
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}