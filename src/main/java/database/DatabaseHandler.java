package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.Book;
import domain.Member;

public class DatabaseHandler {

    public static boolean isBookExists(Connection conn, String bookId) throws SQLException {
        String query = "SELECT 1 FROM Book WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static void insertBook(Connection conn, String bookId, String authorId, int publishYear, String title, String subject) throws SQLException {
        String query = "INSERT INTO Book (book_id, author_id, publish_year, title, subject) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookId);
            stmt.setString(2, authorId);
            stmt.setInt(3, publishYear);
            stmt.setString(4, title);
            stmt.setString(5, subject);
            stmt.executeUpdate();
        }
    }

    public static boolean isAuthorExists(Connection conn, String authorId) throws SQLException {
        String query = "SELECT 1 FROM Author WHERE author_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, authorId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static void insertAuthor(Connection conn, String authorId, String firstName, String lastName) throws SQLException {
        String query = "INSERT INTO Author (author_id, first_name, last_name) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, authorId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.executeUpdate();
        }
    }
    
    public static Member login(String email, String password) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Members WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            } else {
                return null;
            }
        } finally {
            conn.close();
        }
    }
    
    public static boolean insertMember(String name, String lastName, String email, String password) throws SQLException {
        // SQL query for inserting a new member
        String sql = "INSERT INTO Members (name, lastname, email, password) VALUES (?, ?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // set the parameters of the statement
            stmt.setString(1, name);
            stmt.setString(2, lastName); 
            stmt.setString(3, email); 
            stmt.setString(4, password); 

            int result = stmt.executeUpdate();
            return result > 0; // return true if insertion was successful
        } finally {
            conn.close();
        }
    }
    
    public static List<Book> searchBooks(String title, String subject, String authorName, String authorLastName,
                                         int fromPublishYear, int toPublishYear, String bookId) throws SQLException {
        // early exit if all parameters are empty
        if ((title == null || title.isEmpty()) && (subject == null || subject.isEmpty()) &&
            (authorName == null || authorName.isEmpty()) && (authorLastName == null || authorLastName.isEmpty()) && (bookId == null)) {
            return new ArrayList<>(); // Return empty list if no criteria are provided
        }
        
        StringBuilder query = new StringBuilder("SELECT * FROM Book WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
            parameters.add("%" + title + "%");
        }
        if (subject != null && !subject.isEmpty()) {
            query.append(" AND subject = ?");
            parameters.add(subject);
        }
        if (authorName != null && !authorName.isEmpty() && authorLastName != null && !authorLastName.isEmpty()) {
            // Search for both first name and last name
            query.append(" AND author_id IN (SELECT author_id FROM Author WHERE first_name LIKE ? AND last_name LIKE ?)");
            parameters.add("%" + authorName + "%");
            parameters.add("%" + authorLastName + "%");
        } else if (authorName != null && !authorName.isEmpty()) {
            // Search only for first name
            query.append(" AND author_id IN (SELECT author_id FROM Author WHERE first_name LIKE ?)");
            parameters.add("%" + authorName + "%");
        } else if (authorLastName != null && !authorLastName.isEmpty()) {
            // Search only for last name
            query.append(" AND author_id IN (SELECT author_id FROM Author WHERE last_name LIKE ?)");
            parameters.add("%" + authorLastName + "%");
        }
        
        if (fromPublishYear != 0 && toPublishYear != 0) {
            query.append(" AND publish_year BETWEEN ? AND ?");
            parameters.add(fromPublishYear);
            parameters.add(toPublishYear);
        }
        if (bookId != null && !bookId.isEmpty()) {
            query.append(" AND book_id = ?");
            parameters.add(bookId);
        }
        
        System.out.println("Search query: " + query.toString());  // Log the query

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(extractBookInfo(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Database search operation failed.", e);
        }
    }

    // Extract Book Info method
    private static Book extractBookInfo(ResultSet rs) throws SQLException {
        String bookId = rs.getString("book_id");
        String title = rs.getString("title");
        String authorId = rs.getString("author_id");
        int publishYear = rs.getInt("publish_year");
        String subject = rs.getString("subject");
        Author author = getAuthor(authorId);

        // Create and return Book object
        return new Book(bookId, author, publishYear, title,  subject);
    }

    // Helper method to get the Author object from authorId
    private static Author getAuthor(String authorId) throws SQLException {
        String query = "SELECT * FROM Author WHERE author_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, authorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Author(rs.getString("author_id"), rs.getString("first_name"), rs.getString("last_name"));
            }
        }
        return null; // return null if author not found
    }
}

