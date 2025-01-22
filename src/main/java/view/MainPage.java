package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DatabaseHandler;

import java.awt.*;
import java.util.List;
import java.util.Map;

import domain.Member;

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

        // Tab 1: Most Borrowed Authors (Dynamic Query)
        tabbedPane.addTab(" Pop. Authors", createDynamicAnalysisPanel( //
                "Most Popular Authors Of The Year 2024 Based on Borrowings",
                DatabaseHandler.getMostBorrowedAuthorsWithTotalBooks()
        ));

        // Tab 2: Books by Subject
        tabbedPane.addTab("Books Not Return.", createOverdueBooksPanel()); //Books Not Return.

        // Tab 3: Top Books By Subject
        tabbedPane.addTab("Top Books by Subj.", createTopBooksPanel()); //Top Books by Subj.
        
        // Tab 4: Authors by Avg. Book Price
        tabbedPane.addTab("Authors by Avg. Pr.", createAuthorsByAveragePricePanel()); // Authors by Avg. Pr.
        
        // Tab 5: High Spending Members        
        tabbedPane.addTab("Top Spending Memb.", createHighSpendingMembersPanel()); // Top Spending Memb.

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

        JButton myBooksButton = new JButton("My Books");
        myBooksButton.setFont(new Font("Arial", Font.BOLD, 14));
        myBooksButton.setBackground(Color.WHITE);
        myBooksButton.addActionListener(e -> {
            new MemberBooksPage(member).setVisible(true);
            dispose();
        });

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

        buttonPanel.add(myBooksButton);
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

    private JPanel createDynamicAnalysisPanel(String title, List<String> data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String entry : data) {
            model.addElement(entry);
        }

        JList<String> list = new JList<>(model);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createOverdueBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Books Not Returned Yet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Fetch overdue books from the database
        List<Map<String, Object>> overdueBooks = DatabaseHandler.getOverdueBooks();

        // Create a table model
        String[] columnNames = {"Book Title", "Copy ID", "Borrower Name", "Borrow Date", "Due Date", "Days Overdue"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Map<String, Object> book : overdueBooks) {
            tableModel.addRow(new Object[]{
                book.get("book_title"),
                book.get("copy_id"),
                book.get("borrower_name") + " " + book.get("borrower_lastname"),
                book.get("borrow_date"),
                book.get("due_date"),
                book.get("days_overdue")
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTopBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Locations Of The 3 Most Popular Books Of Each Subject", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Fetch top books from the database
        List<Map<String, Object>> topBooks = DatabaseHandler.getTopBooksBySubject();

        // Create a table model
        String[] columnNames = {"Subject", "Book Title", "Copy ID", "Location", "Times Borrowed"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Map<String, Object> book : topBooks) {
            tableModel.addRow(new Object[]{
                book.get("subject"),
                book.get("book_title"),
                book.get("copy_id"),
                book.get("location"),
                book.get("times_borrowed")
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    
    private JPanel createAuthorsByAveragePricePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Authors By Average Book Price", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Fetch data from the database
        List<Map<String, Object>> authorsData = DatabaseHandler.getAuthorsByAverageBookPrice();

        // Create a table model
        String[] columnNames = {"Author ID", "First Name", "Last Name", "Average Book Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Map<String, Object> author : authorsData) {
            tableModel.addRow(new Object[]{
                    author.get("author_id"),
                    author.get("first_name"),
                    author.get("last_name"),
                    String.format("$%.2f", author.get("average_price"))
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createHighSpendingMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Total Amount Of Spendings Of Each Member (> $30)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Fetch members with high spending from the database
        List<Map<String, Object>> highSpendingMembers = DatabaseHandler.getMembersWithHighSpending();

        // Create a table model
        String[] columnNames = {"Member ID", "Member Name", "Total Spending"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Map<String, Object> member : highSpendingMembers) {
            tableModel.addRow(new Object[]{
                member.get("member_id"),
                member.get("member_name"),
                member.get("total_spending")
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }
    
    
}