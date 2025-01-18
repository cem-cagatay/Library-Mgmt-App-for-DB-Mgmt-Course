package view;
import javax.swing.*;

import domain.Member;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class SearchResultsPage extends JFrame {
	
	private Member member;

    public SearchResultsPage(List<Map<String, String>> bookResults, Member member) {
    	
    	this.member = member;
        setTitle("Search Results - KUtüp Library Management System");
        setSize(800, 600);
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
        JLabel titleLabel = new JLabel("Search Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Results Panel
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);

        // Add books to the results panel
        for (Map<String, String> book : bookResults) {
            JButton bookButton = new JButton(book.get("Title"));
            bookButton.setFont(new Font("Arial", Font.PLAIN, 16));
            bookButton.setBackground(Color.WHITE);
            bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookButton.addActionListener(e -> new BookDetailsPage(book).setVisible(true)); // Open BookDetailsPage
            resultsPanel.add(bookButton);
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        }

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel with Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            new SearchPage(member).setVisible(true); // navigate back to SearchPage
            dispose();
        });

        bottomPanel.add(backButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
