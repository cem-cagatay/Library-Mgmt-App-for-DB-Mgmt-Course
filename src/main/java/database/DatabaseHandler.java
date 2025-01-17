package database;

import java.sql.*;

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
}

