package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BookDetailsPage extends JFrame {

    public BookDetailsPage(Map<String, String> bookDetails) {
        setTitle("Book Details - KUtüp Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Title Label
        JLabel titleLabel = new JLabel(bookDetails.get("Title"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Book Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (Map.Entry<String, String> entry : bookDetails.entrySet()) {
            if (!entry.getKey().equals("Title")) {
                JLabel detailLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
                detailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                detailLabel.setForeground(Color.WHITE);
                detailsPanel.add(detailLabel);
            }
        }

        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
