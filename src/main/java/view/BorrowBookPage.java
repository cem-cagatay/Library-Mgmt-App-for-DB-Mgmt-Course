package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import com.toedter.calendar.JDateChooser;

import database.DatabaseHandler;
import domain.BookCopy;
import domain.Borrow;
import domain.Member;

public class BorrowBookPage extends JFrame {

    public BorrowBookPage(BookCopy bookCopy, Member member) {
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
                + "<h2>You are borrowing \"" + bookCopy.getCopyId() + "\"!</h2>"
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
        startDateChooser.setDateFormatString("yyyy-MM-dd"); 
        startDateChooser.setLocale(Locale.ENGLISH);

        // End Date label and field next to each other
        JLabel endDateLabel = new JLabel("Pick End Date:");
        endDateLabel.setForeground(Color.WHITE);
        endDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Using JDateChooser for end date
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd"); 
        endDateChooser.setLocale(Locale.ENGLISH);

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
                // Create a Borrow object
                LocalDate borrowDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dueDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Borrow borrow = new Borrow(member, bookCopy, borrowDate, dueDate);

                // save borrowing details and update book copy status
                boolean borrowSaved = DatabaseHandler.saveBorrowToDatabase(borrow);
                boolean statusUpdated = DatabaseHandler.updateBookCopyStatus(bookCopy.getCopyId(), "Unavailable");

                if (borrowSaved && statusUpdated) {
                	member.invalidateBorrowedBooksCache(); // Invalidate the cache here
                    JOptionPane.showMessageDialog(
                            this,
                            "<html><div style='text-align: center;'>"
                                    + "<h2>Borrowing Confirmed!</h2>"
                                    + "<p>You have borrowed \"" + bookCopy.getBook().getTitle() + "\".</p>"
                                    + "<p>From: " + borrowDate + " To: " + dueDate + "</p>"
                                    + "</div></html>",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose(); // close the BorrowBookPage after confirmation
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error occurred during borrowing. Please try again.",
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
