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

        JLabel welcomeLabel = new JLabel("Welcome to KUtüp!", SwingConstants.CENTER);
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

        // Tab 1: Most Borrowed Authors
        JPanel mostBorrowedAuthorsPanel = new JPanel(new BorderLayout());
        mostBorrowedAuthorsPanel.setBackground(Color.WHITE);

        JLabel authorsTitle = new JLabel("Most Borrowed Authors", SwingConstants.CENTER);
        authorsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mostBorrowedAuthorsPanel.add(authorsTitle, BorderLayout.NORTH);

        JList<String> authorsList = new JList<>(new String[]{
            "1. J.K. Rowling - 50 borrows",
            "2. George Orwell - 45 borrows",
            "3. Harper Lee - 40 borrows",
            "4. F. Scott Fitzgerald - 38 borrows",
            "5. J.R.R. Tolkien - 35 borrows"
        });
        authorsList.setFont(new Font("Arial", Font.PLAIN, 16));
        mostBorrowedAuthorsPanel.add(new JScrollPane(authorsList), BorderLayout.CENTER);

        tabbedPane.addTab("Most Borrowed Authors", mostBorrowedAuthorsPanel);

        // Tab 2: Late Returns
        JPanel lateReturnsPanel = new JPanel(new BorderLayout());
        lateReturnsPanel.setBackground(Color.WHITE);

        JLabel lateReturnsTitle = new JLabel("Books with the Most Late Returns", SwingConstants.CENTER);
        lateReturnsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lateReturnsPanel.add(lateReturnsTitle, BorderLayout.NORTH);

        JList<String> lateReturnsList = new JList<>(new String[]{
            "1. The Great Gatsby - 10 late returns",
            "2. 1984 - 8 late returns",
            "3. To Kill a Mockingbird - 7 late returns",
            "4. Harry Potter and the Chamber of Secrets - 6 late returns",
            "5. Moby Dick - 5 late returns"
        });
        lateReturnsList.setFont(new Font("Arial", Font.PLAIN, 16));
        lateReturnsPanel.add(new JScrollPane(lateReturnsList), BorderLayout.CENTER);

        tabbedPane.addTab("Late Returns", lateReturnsPanel);

        // Tab 3: Books by Subject
        JPanel booksBySubjectPanel = new JPanel(new BorderLayout());
        booksBySubjectPanel.setBackground(Color.WHITE);

        JLabel subjectTitle = new JLabel("Books by Subject", SwingConstants.CENTER);
        subjectTitle.setFont(new Font("Arial", Font.BOLD, 18));
        booksBySubjectPanel.add(subjectTitle, BorderLayout.NORTH);

        JList<String> subjectList = new JList<>(new String[]{
            "1. Fiction - 120 borrows",
            "2. Mystery - 90 borrows",
            "3. Science - 80 borrows",
            "4. History - 75 borrows",
            "5. Fantasy - 70 borrows"
        });
        subjectList.setFont(new Font("Arial", Font.PLAIN, 16));
        booksBySubjectPanel.add(new JScrollPane(subjectList), BorderLayout.CENTER);

        tabbedPane.addTab("Books by Subject", booksBySubjectPanel);

        splitPane.setTopComponent(tabbedPane);

        // General Information Section
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.LIGHT_GRAY);

        JLabel generalInfo = new JLabel("<html><div style='text-align: center;'>KUtüp is your ultimate library management system.<br>Our mission is to streamline library processes and enhance user experiences.<br>Manage your books, authors, and borrowers with ease and efficiency.<br>Stay organized, save time, and enjoy seamless library operations!</div></html>", SwingConstants.CENTER);
        generalInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        generalInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(generalInfo, BorderLayout.CENTER);
        splitPane.setBottomComponent(infoPanel);

        backgroundPanel.add(splitPane, BorderLayout.CENTER);

        // Bottom Section: Search and Logout Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel memberNameLabelBottom = new JLabel("Logged in as: " + member.getFirstName() + " " + member.getLastName(), SwingConstants.LEFT);
        memberNameLabelBottom.setFont(new Font("Arial", Font.PLAIN, 16));
        memberNameLabelBottom.setForeground(Color.WHITE);
        memberNameLabelBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(memberNameLabelBottom, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.addActionListener(e -> {
            new SearchPage(member).setVisible(true); // Pass Member to SearchPage
            dispose();
        });


        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}