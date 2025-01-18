package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.Member;

public class SearchPage extends JFrame {

    public SearchPage(Member member) {
        setTitle("Search - KUtüp Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/assets/logo2.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Motivational and Instructional Text
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h1>Discover Your Next Favorite Book!</h1>"
                + "Use the filters below to narrow your search.<br>"
                + "Search by subject, publication year, title, or book ID.<br>"
                + "</div></html>", SwingConstants.CENTER);
        infoLabel.setBounds(50, 20, 700, 80); 
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        backgroundPanel.add(infoLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true); // Navigate back to MainPage
            dispose();
        });
        backgroundPanel.add(backButton);

        // Subject Label and Dropdown
        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(50, 120, 100, 30);
        subjectLabel.setForeground(Color.WHITE);
        backgroundPanel.add(subjectLabel);

        String[] subjects = {"Love", "Fiction", "Text Books"};
        JComboBox<String> subjectDropdown = new JComboBox<>(subjects);
        subjectDropdown.setBounds(150, 120, 150, 30);
        backgroundPanel.add(subjectDropdown);

        // Publish Year Range
        JLabel publishYearLabel = new JLabel("Publish Year:");
        publishYearLabel.setBounds(50, 170, 100, 30);
        publishYearLabel.setForeground(Color.WHITE);
        backgroundPanel.add(publishYearLabel);

        JTextField fromYearField = new JTextField();
        fromYearField.setBounds(150, 170, 70, 30);
        backgroundPanel.add(fromYearField);

        JLabel toLabel = new JLabel("to");
        toLabel.setBounds(230, 170, 20, 30);
        toLabel.setForeground(Color.WHITE);
        backgroundPanel.add(toLabel);

        JTextField toYearField = new JTextField();
        toYearField.setBounds(260, 170, 70, 30);
        backgroundPanel.add(toYearField);

        // Title Field
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(50, 220, 100, 30);
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(150, 220, 200, 30);
        backgroundPanel.add(titleField);

        // Book ID Field
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(50, 270, 100, 30);
        bookIdLabel.setForeground(Color.WHITE);
        backgroundPanel.add(bookIdLabel);

        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(150, 270, 200, 30);
        backgroundPanel.add(bookIdField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(150, 350, 100, 30);
        searchButton.addActionListener(e -> {
            String selectedSubject = (String) subjectDropdown.getSelectedItem();
            String fromYear = fromYearField.getText();
            String toYear = toYearField.getText();
            String title = titleField.getText();
            String bookId = bookIdField.getText();

            // Fake book data for demonstration
            List<Map<String, String>> bookResults = new ArrayList<>();

            Map<String, String> book1 = new HashMap<>();
            book1.put("Title", "The Great Gatsby");
            book1.put("Author", "F. Scott Fitzgerald");
            book1.put("Year", "1925");
            book1.put("Genre", "Fiction");
            book1.put("Description", "A novel about the American dream.");
            bookResults.add(book1);

            Map<String, String> book2 = new HashMap<>();
            book2.put("Title", "To Kill a Mockingbird");
            book2.put("Author", "Harper Lee");
            book2.put("Year", "1960");
            book2.put("Genre", "Fiction");
            book2.put("Description", "A story of racial injustice in the Deep South.");
            bookResults.add(book2);

            Map<String, String> book3 = new HashMap<>();
            book3.put("Title", "1984");
            book3.put("Author", "George Orwell");
            book3.put("Year", "1949");
            book3.put("Genre", "Dystopian");
            book3.put("Description", "A novel about a totalitarian regime and surveillance.");
            bookResults.add(book3);

            // Navigate to SearchResultsPage with the search results
            new SearchResultsPage(bookResults, member).setVisible(true);
            dispose();
        });
        backgroundPanel.add(searchButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
