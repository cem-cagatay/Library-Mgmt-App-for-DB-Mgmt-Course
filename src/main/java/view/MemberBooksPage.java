package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import domain.Member;
import domain.BookCopy;
import database.DatabaseHandler;

public class MemberBooksPage extends JFrame {

    public MemberBooksPage(Member member) {
        setTitle("My Books - KUtüp Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs for Borrowed and Purchased Books
        JTabbedPane tabbedPane = new JTabbedPane();

        // Borrowed Books Tab
        List<BookCopy> borrowedBooks = DatabaseHandler.getBorrowedBooks(member);
        JPanel borrowedPanel = createBooksPanel(borrowedBooks, "Borrowed Books");
        tabbedPane.addTab("Borrowed Books", borrowedPanel);

        // Purchased Books Tab
        List<BookCopy> purchasedBooks = DatabaseHandler.getPurchasedBooks(member);
        JPanel purchasedPanel = createBooksPanel(purchasedBooks, "Purchased Books");
        tabbedPane.addTab("Purchased Books", purchasedPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBooksPanel(List<BookCopy> books, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (BookCopy bookCopy : books) {
            model.addElement(String.format("Book: %s | Copy ID: %d | Price: %.2f | Status: %s",
                    bookCopy.getBook().getTitle(), bookCopy.getCopyId(), bookCopy.getPrice(), bookCopy.getStatus()));
        }

        JList<String> bookList = new JList<>(model);
        bookList.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        return panel;
    }
}