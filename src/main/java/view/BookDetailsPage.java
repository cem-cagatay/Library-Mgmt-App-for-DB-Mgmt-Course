package view;

import javax.swing.*;

import database.DatabaseHandler;
import domain.Book;
import domain.BookCopy;
import domain.Member;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookDetailsPage extends JFrame {

    public BookDetailsPage(Member member, Book book) {
        setTitle("Book Details - KUtüp Library Management System");
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
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        // Fetch the available copies once
        List<BookCopy> availableCopies = new ArrayList<>();
        try {
            availableCopies = DatabaseHandler.getAvailableBookCopies(book);
            JLabel availableCopiesLabel = new JLabel("Available Copies: " + availableCopies.size());
            availableCopiesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            availableCopiesLabel.setForeground(Color.WHITE);
            detailsPanel.add(availableCopiesLabel);
            
         // add the "See Available Copies" button only if there are available copies
            if (!availableCopies.isEmpty()) {
                JButton seeCopiesButton = new JButton("See Available Copies");
                seeCopiesButton.setFont(new Font("Arial", Font.BOLD, 14));
                seeCopiesButton.setBackground(Color.WHITE);

                // pass available copies to the next page
                List<BookCopy> finalAvailableCopies = availableCopies;
                seeCopiesButton.addActionListener(e -> new AvailableCopiesPage(finalAvailableCopies).setVisible(true));
                buttonPanel.add(seeCopiesButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching available copies: " + e.getMessage());
        }

        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            dispose(); // close the current window
        });
        

        buttonPanel.add(backButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}