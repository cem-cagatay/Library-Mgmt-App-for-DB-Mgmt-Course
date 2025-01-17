package database;

import java.sql.*;

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
}

