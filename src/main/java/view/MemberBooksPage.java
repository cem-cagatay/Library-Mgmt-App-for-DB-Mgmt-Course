package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import domain.Member;
import domain.BookCopy;
import domain.Borrow;
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

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBorrowedBooksPanel(Member member) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Borrowed Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Book Title", "Copy ID", "Due Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table with borrowed books data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH); // dates are in English
        for (Borrow borrow : member.getBorrowedBooks()) {
            BookCopy bookCopy = borrow.getCopy();
            LocalDate dueDate = borrow.getDueDate();
            LocalDate now = LocalDate.now();
            String status = dueDate.isBefore(now)
                            ? String.format("%d days late", java.time.temporal.ChronoUnit.DAYS.between(dueDate, now))
                            : "Not overdue";

            tableModel.addRow(new Object[]{
                bookCopy.getBook().getTitle(),
                bookCopy.getCopyId(),
                dueDate.format(formatter),
                status
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add Return and Back buttons below the table
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnButton.setEnabled(false); // Initially disabled until a row is selected

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add a row selection listener to enable the Return button
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                returnButton.setEnabled(true);
            }
        });

        // Add an action listener for the Return button
        returnButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Fetch the selected borrow details
                String copyId = table.getValueAt(selectedRow, 1).toString();

                // Logic to return the book
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to return this book?", 
                        "Confirm Return", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Perform the return operation
                    boolean returnSuccessful = DatabaseHandler.returnBorrowedBook(member, Integer.parseInt(copyId));
                    if (returnSuccessful) {
                        JOptionPane.showMessageDialog(panel, "Book returned successfully!");
                        member.invalidateBorrowedBooksCache();
                        ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Error returning book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add an action listener for the Back button
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(returnButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createPurchasedBooksPanel(Member member) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Purchased Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Book Title", "Copy ID", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table with purchased books data
        for (BookCopy bookCopy : member.getPurchasedBooks()) {
            tableModel.addRow(new Object[]{
                bookCopy.getBook().getTitle(),
                bookCopy.getCopyId(),
                String.format("%.2f", bookCopy.getPrice())
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add scroll pane to table
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Add an action listener for the Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}