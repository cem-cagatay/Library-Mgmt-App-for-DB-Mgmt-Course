package view;

import javax.swing.*;

import domain.Book;

import java.awt.*;
import java.util.Map;

public class BookDetailsPage extends JFrame {

    public BookDetailsPage(Book book) {
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
        JLabel titleLabel = new JLabel(book.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Book Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Displaying Book Attributes
        JLabel bookIdLabel = new JLabel("Book ID: " + book.getBookId());
        bookIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bookIdLabel.setForeground(Color.WHITE);
        detailsPanel.add(bookIdLabel);

        JLabel authorIdLabel = new JLabel("Author: " + book.getAuthor().getFirstName() + " "+ book.getAuthor().getLastName());
        authorIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        authorIdLabel.setForeground(Color.WHITE);
        detailsPanel.add(authorIdLabel);

        JLabel publishYearLabel = new JLabel("Publish Year: " + book.getPublishYear());
        publishYearLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        publishYearLabel.setForeground(Color.WHITE);
        detailsPanel.add(publishYearLabel);

        JLabel subjectLabel = new JLabel("Subject: " + book.getSubject());
        subjectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subjectLabel.setForeground(Color.WHITE);
        detailsPanel.add(subjectLabel);

        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            dispose(); // close the current window
        });
        
     // Buy Button
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyButton.setBackground(Color.WHITE);
        buyButton.addActionListener(e -> {
            new BuyBookPage(book.getTitle()).setVisible(true);
        });
        buttonPanel.add(buyButton);

        // Borrow Button
        JButton borrowButton = new JButton("Borrow");
        borrowButton.setFont(new Font("Arial", Font.BOLD, 14));
        borrowButton.setBackground(Color.WHITE);
        borrowButton.addActionListener(e -> {
            new BorrowBookPage(book.getTitle()).setVisible(true); // open BorrowBookPage
        });

        buttonPanel.add(borrowButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(backButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}