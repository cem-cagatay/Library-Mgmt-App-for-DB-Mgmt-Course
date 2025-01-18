package view;

import javax.swing.*;
import java.awt.*;
import domain.Member;
import view.SearchPage;


public class MainPage extends JFrame {

    public MainPage(Member member) {
        setTitle("Homepage - KUtüp Library Management System");
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

        // Top Section: Welcome Label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome to KUtüp Library Management System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Middle Section: Split Panel for Analyses and General Information
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.7); // 70% for analyses, 30% for information

        // Analyses Section
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(230, 230, 250));
        UIManager.put("TabbedPane.selected", new Color(100, 149, 237));
        UIManager.put("TabbedPane.unselectedBackground", Color.WHITE);

        // Tab 1: Most Borrowed Authors
        tabbedPane.addTab("Most Borrowed Authors", createAnalysisPanel(
                "Most Borrowed Authors",
                new String[]{
                        "1. J.K. Rowling - 50 borrows",
                        "2. George Orwell - 45 borrows",
                        "3. Harper Lee - 40 borrows",
                        "4. F. Scott Fitzgerald - 38 borrows",
                        "5. J.R.R. Tolkien - 35 borrows"
                }));

        // Tab 2: Late Returns
        tabbedPane.addTab("Late Returns", createAnalysisPanel(
                "Books with the Most Late Returns",
                new String[]{
                        "1. The Great Gatsby - 10 late returns",
                        "2. 1984 - 8 late returns",
                        "3. To Kill a Mockingbird - 7 late returns",
                        "4. Harry Potter and the Chamber of Secrets - 6 late returns",
                        "5. Moby Dick - 5 late returns"
                }));

        // Tab 3: Books by Subject
        tabbedPane.addTab("Books by Subject", createAnalysisPanel(
                "Books by Subject",
                new String[]{
                        "1. Fiction - 120 borrows",
                        "2. Mystery - 90 borrows",
                        "3. Science - 80 borrows",
                        "4. History - 75 borrows",
                        "5. Fantasy - 70 borrows"
                }));

        // Tab 4: Top Borrowers
        tabbedPane.addTab("Top Borrowers", createAnalysisPanel(
                "Top Borrowers",
                new String[]{
                        "1. Alice Johnson - 20 books",
                        "2. Bob Smith - 18 books",
                        "3. Charlie Brown - 15 books",
                        "4. Diana Prince - 12 books",
                        "5. Ethan Hunt - 10 books"
                }));

        // Tab 5: Popular Book Copies
        tabbedPane.addTab("Popular Book Copies", createAnalysisPanel(
                "Popular Book Copies",
                new String[]{
                        "1. Copy 101 - 25 borrows",
                        "2. Copy 102 - 20 borrows",
                        "3. Copy 103 - 18 borrows",
                        "4. Copy 104 - 15 borrows",
                        "5. Copy 105 - 10 borrows"
                }));

        splitPane.setTopComponent(tabbedPane);

        // General Information Section
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.LIGHT_GRAY);

        JLabel generalInfo = new JLabel("<html><div style='text-align: center;'>"
                + "KUtüp is your ultimate library management system.<br>"
                + "Our mission is to streamline library processes and enhance user experiences.<br>"
                + "Manage your books, authors, and borrowers with ease and efficiency.<br>"
                + "Stay organized, save time, and enjoy seamless library operations!<br>"
                + "Also We Love COMP306"
                + "</div></html>", SwingConstants.CENTER);
        generalInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        generalInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(generalInfo, BorderLayout.CENTER);
        splitPane.setBottomComponent(infoPanel);

        backgroundPanel.add(splitPane, BorderLayout.CENTER);

        // Footer Section: Buttons, Copyright, and Logged in as
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.addActionListener(e -> {
            new SearchPage(member).setVisible(true);
            dispose();
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);
        footerPanel.add(buttonPanel, BorderLayout.NORTH);

        // Footer Text
        JLabel copyrightLabel = new JLabel("© 2025 KUtüp Library Management System - Version 1.0", SwingConstants.CENTER);
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightLabel.setForeground(Color.WHITE);

        JLabel loggedInAsLabel = new JLabel("Logged in as: " + member.getFirstName() + " " + member.getLastName(), SwingConstants.CENTER);
        loggedInAsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loggedInAsLabel.setForeground(Color.WHITE);

        JPanel bottomTextPanel = new JPanel(new GridLayout(2, 1));
        bottomTextPanel.setOpaque(false);
        bottomTextPanel.add(loggedInAsLabel);
        bottomTextPanel.add(copyrightLabel);

        footerPanel.add(bottomTextPanel, BorderLayout.SOUTH);

        backgroundPanel.add(footerPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createAnalysisPanel(String title, String[] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JList<String> list = new JList<>(data);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        return panel;
    }
}
