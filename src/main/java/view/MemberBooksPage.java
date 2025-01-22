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
        setTitle("My Books - Kütüp Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs for Borrowed and Purchased Books
        JTabbedPane tabbedPane = new JTabbedPane();

        // Borrowed Books Tab
        JPanel borrowedBooksPanel = createBorrowedBooksPanel(member);
        tabbedPane.addTab("Borrowed Books", borrowedBooksPanel);

        // Purchased Books Tab
        JPanel purchasedBooksPanel = createPurchasedBooksPanel(member);
        tabbedPane.addTab("Purchased Books", purchasedBooksPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Bottom Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Return Button
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnButton.setBackground(Color.WHITE);

        returnButton.addActionListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) { // Borrowed Books Tab
                JList<BookCopy> bookList = (JList<BookCopy>) ((JScrollPane) borrowedBooksPanel.getComponent(1)).getViewport().getView();
                BookCopy selectedBook = bookList.getSelectedValue();
                if (selectedBook == null) {
                    JOptionPane.showMessageDialog(this, "Please select a book to return.");
                } else {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to return this book?", "Confirm Return", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean returnSuccessful = DatabaseHandler.returnBorrowedBook(member, selectedBook);
                        if (returnSuccessful) {
                            JOptionPane.showMessageDialog(this, "Book returned successfully!");
                            member.invalidateBorrowedBooksCache();
                            ((DefaultListModel<BookCopy>) bookList.getModel()).removeElement(selectedBook);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error returning book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true);
            dispose();
        });

        buttonPanel.add(returnButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Tab change listener to show and hide the Return button
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) { // Borrowed Books Tab
                returnButton.setVisible(true);
            } else { // Purchased Books Tab
                returnButton.setVisible(false);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBorrowedBooksPanel(Member member) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Borrowed Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<BookCopy> model = new DefaultListModel<>();
        for (BookCopy bookCopy : member.getBorrowedBooks()) {
            model.addElement(bookCopy);
        }

        JList<BookCopy> bookList = new JList<>(model);
        bookList.setFont(new Font("Arial", Font.PLAIN, 14));
        bookList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                BookCopy bookCopy = (BookCopy) value;
                String displayText = String.format("Book: %s | Copy ID: %d | Due Date: %s",
                        bookCopy.getBook().getTitle(), bookCopy.getCopyId(), bookCopy.getStatus());
                return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
            }
        });

        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPurchasedBooksPanel(Member member) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Purchased Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (BookCopy bookCopy : member.getPurchasedBooks()) {
            model.addElement(String.format("Book: %s | Copy ID: %d | Price: %.2f",
                    bookCopy.getBook().getTitle(), bookCopy.getCopyId(), bookCopy.getPrice()));
        }

        JList<String> bookList = new JList<>(model);
        bookList.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        return panel;
    }
}