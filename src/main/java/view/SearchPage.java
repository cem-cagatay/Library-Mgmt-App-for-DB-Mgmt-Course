package view;

import javax.swing.*;

import database.DatabaseHandler;
import domain.Book;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import domain.Member;

public class SearchPage extends JFrame {
	
	private JScrollPane scrollPane; 
	private Member member;

    public SearchPage(Member member) {
    	
    	this.member = member;
    	
        setTitle("Search - Kütüp Library Management System");
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
                + "<p>Books open up new worlds. Search now to begin your journey!</p>"
                + "<p>Use the filters below to narrow your search.<br>"
                + "Search by subject, publication year, title, or book ID.</p>"
                + "</div></html>", SwingConstants.CENTER);
        infoLabel.setBounds(50, 20, 700, 100); 
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        backgroundPanel.add(infoLabel);
        
        // Footer Section
        JLabel footerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "© 2025 KUtüp Library Management System. Version 1.0<br>"
                + "All rights reserved. | Contact: support@kutup.com"
                + "</div></html>", SwingConstants.CENTER);
        footerLabel.setBounds(0, 525, 800, 40);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.LIGHT_GRAY);
        backgroundPanel.add(footerLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 480, 100, 30);
        backButton.addActionListener(e -> {
            new MainPage(member).setVisible(true); // Navigate back to MainPage
            dispose();
        });
        backgroundPanel.add(backButton);

        // Subject Label and Dropdown
        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(50, 130, 100, 30);
        subjectLabel.setForeground(Color.WHITE);
        backgroundPanel.add(subjectLabel);

        String[] subjects = {"All", "love", "fiction", "textbooks"};
        JComboBox<String> subjectDropdown = new JComboBox<>(subjects);
        subjectDropdown.setBounds(150, 130, 150, 30);
        backgroundPanel.add(subjectDropdown);

        // Publish Year Range
        JLabel publishYearLabel = new JLabel("Publish Year:");
        publishYearLabel.setBounds(50, 180, 100, 30);
        publishYearLabel.setForeground(Color.WHITE);
        backgroundPanel.add(publishYearLabel);

        JTextField fromYearField = new JTextField();
        fromYearField.setBounds(150, 180, 70, 30);
        backgroundPanel.add(fromYearField);

        JLabel toLabel = new JLabel("to");
        toLabel.setBounds(230, 180, 20, 30);
        toLabel.setForeground(Color.WHITE);
        backgroundPanel.add(toLabel);

        JTextField toYearField = new JTextField();
        toYearField.setBounds(260, 180, 70, 30);
        backgroundPanel.add(toYearField);

        // Title Field
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(50, 230, 100, 30);
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(150, 230, 150, 30);
        backgroundPanel.add(titleField);

        // Book ID Field
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(50, 280, 100, 30);
        bookIdLabel.setForeground(Color.WHITE);
        backgroundPanel.add(bookIdLabel);

        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(150, 280, 150, 30);
        backgroundPanel.add(bookIdField);
        
     
        JLabel authorFirstNameLabel = new JLabel("Author Name:");
        authorFirstNameLabel.setBounds(50, 330, 150, 30);
        authorFirstNameLabel.setForeground(Color.WHITE);
        backgroundPanel.add(authorFirstNameLabel);

        JTextField authorFirstNameField = new JTextField();
        authorFirstNameField.setBounds(150, 330, 150, 30);
        backgroundPanel.add(authorFirstNameField);

        JLabel authorLastNameLabel = new JLabel("Last Name:");
        authorLastNameLabel.setBounds(50, 380, 150, 30);
        authorLastNameLabel.setForeground(Color.WHITE);
        backgroundPanel.add(authorLastNameLabel);

        JTextField authorLastNameField = new JTextField();
        authorLastNameField.setBounds(150, 380, 150, 30);
        backgroundPanel.add(authorLastNameField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(150, 430, 100, 30);
        searchButton.addActionListener(e -> {
            String selectedSubject = (String) subjectDropdown.getSelectedItem();
            String fromYearText = fromYearField.getText();
            String toYearText = toYearField.getText();
            String title = titleField.getText();
            String bookId = bookIdField.getText();
            String authorFirstName = authorFirstNameField.getText();
            String authorLastName = authorLastNameField.getText();
            
            int fromYear = 0;
            int toYear = 0;

            if (!fromYearText.isEmpty()) {
                fromYear = Integer.parseInt(fromYearText);
            }
            
            if (!toYearText.isEmpty()) {
                toYear = Integer.parseInt(toYearText);
            }
            
            List<Book> bookResults;
            
			try {
				// Modify query to consider "All" as no subject filter
				if ("All".equals(selectedSubject)) {
					bookResults = DatabaseHandler.searchBooks(title, null, authorFirstName, authorLastName, fromYear, toYear, bookId);
					if (bookResults == null || bookResults.isEmpty()) {
					    System.out.println("No books found.");
					}
				} else {
					bookResults = DatabaseHandler.searchBooks(title, selectedSubject, authorFirstName, authorLastName, fromYear, toYear, bookId);
					if (bookResults == null || bookResults.isEmpty()) {
					    System.out.println("No books found.");
					}
				}
				// display the results in a scrollable panel
	            showSearchResults(bookResults);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database error: " + e1.getMessage());
			}
            

        });
        backgroundPanel.add(searchButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showSearchResults(List<Book> bookResults) {
    	
        if (scrollPane != null) {
            getContentPane().remove(scrollPane);  // remove the old scroll pane
        }
        
        // create the results panel with a vertical BoxLayout for scrollable items
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        if (bookResults.isEmpty()) {
            resultsPanel.add(new JLabel("No books found"));
        } else {
            // add each book as a clickable label
            for (Book book : bookResults) {
                JLabel bookLabel = new JLabel(book.getTitle());
                bookLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                bookLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	new BookDetailsPage(member, book).setVisible(true); // open BookDetailsPage
                    	
                    }
                });
                resultsPanel.add(bookLabel);
                
                // add a horizontal separator after each book
                JSeparator separator = new JSeparator();
                separator.setPreferredSize(new Dimension(400, 1));
                resultsPanel.add(separator);
            }
        }

        scrollPane = new JScrollPane(resultsPanel); 
        scrollPane.setBounds(450, 130, 300, 400); 
        getContentPane().add(scrollPane);
        revalidate(); 
        repaint();
    }
}