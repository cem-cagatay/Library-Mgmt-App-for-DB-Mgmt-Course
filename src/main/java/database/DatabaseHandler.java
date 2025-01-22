package database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import domain.Author;
import domain.Book;
import domain.BookCopy;
import domain.Borrow;
import domain.Member;
import domain.Purchase;

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
        Author author = getAuthorById(authorId);

        // Create and return Book object
        return new Book(bookId, author, publishYear, title,  subject);
    }

    // Helper method to get the Author object from authorId
    private static Author getAuthorById(String authorId) throws SQLException {
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
    
    
    public static Book getBookById(String bookId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Book WHERE book_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, bookId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Author author = getAuthorById(rs.getString("author_id")); // fetch the Author
                    return new Book(
                            rs.getString("book_id"),
                            author,
                            rs.getInt("publish_year"),
                            rs.getString("title"),
                            rs.getString("subject")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // return null if the book is not found
    }
    
    
    public static List<BookCopy> getAvailableBookCopies(Book book) throws SQLException {
        String query = "SELECT * FROM Book_Copy WHERE book_id = ? AND status = 'Available'";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getBookId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int copyId = rs.getInt("copy_id");
                String status = rs.getString("status");
                double price = rs.getDouble("price");
                int floorNumber = rs.getInt("floor_number");
                char shelfLetter = rs.getString("shelf_letter").charAt(0);
                int shelfNumber = rs.getInt("shelf_number");

                // create a BookCopy object and add it to the list
                bookCopies.add(new BookCopy(copyId, book, status, price, floorNumber, shelfLetter, shelfNumber));
            }
        }

        return bookCopies;
    }
    
    
    // save purchase to the "Buys" table
    public static boolean savePurchaseToDatabase(Purchase purchase) {
    	Member member =purchase.getMember();
    	BookCopy bookCopy = purchase.getBookCopy();
    	
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Buys (member_id, copy_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, member.getMemberId());
                stmt.setInt(2, bookCopy.getCopyId());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // return true if the purchase was saved successfully
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // return false if there was an error
        }
    }
    
    // Save borrowing details to the "Borrows" table
    public static boolean saveBorrowToDatabase(Borrow borrow) {
    	Member member =borrow.getMember();
    	BookCopy bookCopy = borrow.getCopy();
    	
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Borrows (member_id, copy_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, member.getMemberId());
                stmt.setInt(2, bookCopy.getCopyId());
                stmt.setDate(3, Date.valueOf(borrow.getBorrowDate()));
                stmt.setDate(4, Date.valueOf(borrow.getDueDate()));
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // return true if the borrowing was saved successfully
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // return false if there was an error
        }
    }

 // update billing address in the Buyer table and/or credit card number in the Members table
    public static boolean updateMemberDetails(Member member) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            boolean hasBillingAddress = member.getBillingAddress() != null && !member.getBillingAddress().isEmpty();
            boolean hasCreditCardNumber = member.getCreditCardNumber() != null && !member.getCreditCardNumber().isEmpty();

            boolean billingAddressUpdated = true;
            boolean creditCardUpdated = true;

            // Update the Buyer table for the billing address
            if (hasBillingAddress) {
                String buyerQuery = "UPDATE Buyer SET billing_address = ? WHERE member_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(buyerQuery)) {
                    stmt.setString(1, member.getBillingAddress());
                    stmt.setInt(2, member.getMemberId());
                    billingAddressUpdated = stmt.executeUpdate() > 0;
                }
            }

            // Update the Members table for the credit card number
            if (hasCreditCardNumber) {
                String memberQuery = "UPDATE Members SET credit_card_number = ? WHERE member_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(memberQuery)) {
                    stmt.setString(1, member.getCreditCardNumber());
                    stmt.setInt(2, member.getMemberId());
                    creditCardUpdated = stmt.executeUpdate() > 0;
                }
            }

            if (billingAddressUpdated && creditCardUpdated) {
                conn.commit(); // Commit transaction if both updates succeeded
                return true;
            } else {
                conn.rollback(); // Rollback transaction if any update failed
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of error
        }
    }
    
    public static boolean updateBookCopyStatus(int copyId, String status) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Book_Copy SET status = ? WHERE copy_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, status);
                stmt.setInt(2, copyId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // return true if the status was updated successfully
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // return false if there was an error
        }
    }
    
    public static List<Borrow> getBorrowings(Member member) {
        List<Borrow> borrowedBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
        	String query = 
        		    "SELECT " +
        		    "    b.copy_id, " +
        		    "    b.borrow_date, " +
        		    "    b.due_date, " +
        		    "    b.return_date, " +
        		    "    bc.book_id, " +
        		    "    bc.status, " +
        		    "    bc.price, " +
        		    "    bc.floor_number, " +
        		    "    bc.shelf_letter, " +
        		    "    bc.shelf_number " +
        		    "FROM " +
        		    "    Borrows b " +
        		    "JOIN " +
        		    "    Book_Copy bc ON b.copy_id = bc.copy_id " +
        		    "WHERE " +
        		    "    b.member_id = ? " +
        		    "    AND bc.status = 'Unavailable' " +
        		    "    AND b.borrow_date = ( " +
        		    "        SELECT MAX(b2.borrow_date) " +
        		    "        FROM Borrows b2 " +
        		    "        WHERE b2.copy_id = b.copy_id " +
        		    "          AND b2.member_id = b.member_id " +
        		    "    )";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, member.getMemberId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String bookId = rs.getString("book_id");
                    Book book = getBookById(bookId); // fetch the Book object
                    if (book != null) {
                        BookCopy bookCopy = new BookCopy(
                            rs.getInt("copy_id"),
                            book,
                            rs.getString("status"),
                            rs.getDouble("price"),
                            rs.getInt("floor_number"),
                            rs.getString("shelf_letter").charAt(0),
                            rs.getInt("shelf_number")
                        );

                        Borrow borrow = new Borrow(
                            member,
                            bookCopy,
                            rs.getDate("borrow_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate()
                        );

                        // Set the return date if it exists
                        if (rs.getDate("return_date") != null) {
                            borrow.setReturnDate(rs.getDate("return_date").toLocalDate());
                        }

                        borrowedBooks.add(borrow);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }
    
   
    
    public static List<BookCopy> getPurchasedBooks(Member member) {
        List<BookCopy> purchasedBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT bc.* FROM Buys b JOIN Book_Copy bc ON b.copy_id = bc.copy_id WHERE b.member_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, member.getMemberId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String bookId = rs.getString("book_id");
                    Book book = getBookById(bookId); // fetch the Book object
                    if (book != null) {
                        purchasedBooks.add(new BookCopy(
                                rs.getInt("copy_id"),
                                book,
                                rs.getString("status"),
                                rs.getDouble("price"),
                                rs.getInt("floor_number"),
                                rs.getString("shelf_letter").charAt(0),
                                rs.getInt("shelf_number")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchasedBooks;
    }
    
    public static boolean returnBorrowedBook(Member member, int bookCopyId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update Borrows table with return_date
            String updateBorrowQuery = "UPDATE Borrows SET return_date = ? WHERE member_id = ? AND copy_id = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(updateBorrowQuery)) {
                stmt1.setDate(1, Date.valueOf(LocalDate.now()));
                stmt1.setInt(2, member.getMemberId());
                stmt1.setInt(3, bookCopyId);
                int borrowUpdateCount = stmt1.executeUpdate();

                if (borrowUpdateCount > 0) {
                    // Update Book_Copy table status to Available
                    String updateBookCopyQuery = "UPDATE Book_Copy SET status = 'Available' WHERE copy_id = ?";
                    try (PreparedStatement stmt2 = conn.prepareStatement(updateBookCopyQuery)) {
                        stmt2.setInt(1, bookCopyId);
                        int bookUpdateCount = stmt2.executeUpdate();
                        return bookUpdateCount > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<String> getMostBorrowedAuthorsWithTotalBooks() {
        List<String> result = new ArrayList<>();
        String query = "SELECT " +
                "CONCAT(a.first_name, ' ', a.last_name) AS author_name, " +
                "COUNT(*) AS borrow_count, " +
                "(SELECT COUNT(*) " +
                " FROM Book b " +
                " WHERE b.author_id = a.author_id) AS total_books " +
                "FROM " +
                "Author a " +
                "JOIN Book b ON a.author_id = b.author_id " +
                "JOIN Book_Copy bc ON b.book_id = bc.book_id " +
                "JOIN Borrows br ON bc.copy_id = br.copy_id " +
                "WHERE YEAR(br.borrow_date) = 2024 " +
                "GROUP BY a.author_id, author_name " +
                "ORDER BY borrow_count DESC " +
                "LIMIT 5;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String authorName = rs.getString("author_name");
                int borrowCount = rs.getInt("borrow_count");
                int totalBooks = rs.getInt("total_books");
                result.add(authorName + " - " + borrowCount + " borrows (" + totalBooks + " total books)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public static List<Map<String, Object>> getOverdueBooks() {
        List<Map<String, Object>> overdueBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT " +
                    "B.title AS book_title, " +
                    "BC.copy_id AS copy_id, " +
                    "M.name AS borrower_name, " +
                    "M.lastname AS borrower_lastname, " +
                    "Bo.borrow_date, " +
                    "Bo.due_date, " +
                    "CASE " +
                    "WHEN Bo.due_date < CURDATE() THEN DATEDIFF(CURDATE(), Bo.due_date) " +
                    "ELSE 0 " +
                    "END AS days_overdue " +
                    "FROM " +
                    "Borrows Bo " +
                    "JOIN Book_Copy BC ON Bo.copy_id = BC.copy_id " +
                    "JOIN Book B ON BC.book_id = B.book_id " +
                    "JOIN Members M ON Bo.member_id = M.member_id " +
                    "WHERE Bo.return_date IS NULL " +
                    "ORDER BY days_overdue DESC";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("book_title", rs.getString("book_title"));
                    row.put("copy_id", rs.getInt("copy_id"));
                    row.put("borrower_name", rs.getString("borrower_name"));
                    row.put("borrower_lastname", rs.getString("borrower_lastname"));
                    row.put("borrow_date", rs.getDate("borrow_date"));
                    row.put("due_date", rs.getDate("due_date"));
                    row.put("days_overdue", rs.getInt("days_overdue"));

                    overdueBooks.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdueBooks;
    }
    
    public static List<Map<String, Object>> getTopBooksBySubject() {
        List<Map<String, Object>> topBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = 
                "WITH BookPopularity AS ( " +
                "    SELECT bc.book_id, COUNT(bw.copy_id) AS borrow_count " +
                "    FROM Borrows bw " +
                "    JOIN Book_Copy bc ON bw.copy_id = bc.copy_id " +
                "    GROUP BY bc.book_id " +
                "), RankedBooks AS ( " +
                "    SELECT b.subject, b.title, bp.book_id, " +
                "           ROW_NUMBER() OVER (PARTITION BY b.subject ORDER BY bp.borrow_count DESC) AS ranking " +
                "    FROM BookPopularity bp " +
                "    JOIN Book b ON bp.book_id = b.book_id " +
                "    WHERE b.subject IN ('textbooks', 'love', 'fiction') " +
                "), TopBooks AS ( " +
                "    SELECT subject, title, book_id FROM RankedBooks WHERE ranking <= 3 " +
                ") " +
                "SELECT tb.subject, tb.title AS book_title, bc.copy_id, " +
                "       CONCAT('Floor ', bc.floor_number, ', Shelf ', bc.shelf_letter, bc.shelf_number) AS location, " +
                "       (SELECT COUNT(*) FROM Borrows WHERE copy_id = bc.copy_id) AS times_borrowed " +
                "FROM TopBooks tb " +
                "JOIN Book_Copy bc ON tb.book_id = bc.book_id " +
                "ORDER BY tb.subject, tb.title";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("subject", rs.getString("subject"));
                    row.put("book_title", rs.getString("book_title"));
                    row.put("copy_id", rs.getInt("copy_id"));
                    row.put("location", rs.getString("location"));
                    row.put("times_borrowed", rs.getInt("times_borrowed"));
                    topBooks.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topBooks;
    }
    
    public static List<Map<String, Object>> getAuthorsByAverageBookPrice() {
        List<Map<String, Object>> authorsData = new ArrayList<>();
        String query = "SELECT " +
                "A.author_id, " +
                "A.first_name, " +
                "A.last_name, " +
                "AVG(BC.price) AS average_price " +
                "FROM Author A " +
                "JOIN Book B ON A.author_id = B.author_id " +
                "JOIN Book_Copy BC ON B.book_id = BC.book_id " +
                "GROUP BY A.author_id " +
                "ORDER BY average_price DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("author_id", rs.getString("author_id"));
                row.put("first_name", rs.getString("first_name"));
                row.put("last_name", rs.getString("last_name"));
                row.put("average_price", rs.getDouble("average_price"));
                authorsData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorsData;
    }
    
    public static List<Map<String, Object>> getMembersWithHighSpending() {
        List<Map<String, Object>> membersData = new ArrayList<>();
        String query = "SELECT " +
                "m.member_id, " +
                "CONCAT(m.name, ' ', m.lastname) AS member_name, " +
                "COALESCE(SUM(bc.price), 0) AS total_spending " +
                "FROM Members m " +
                "LEFT JOIN Buys b ON m.member_id = b.member_id " +
                "LEFT JOIN Book_Copy bc ON b.copy_id = bc.copy_id " +
                "GROUP BY m.member_id, m.name, m.lastname " +
                "HAVING total_spending > 30 " +
                "ORDER BY total_spending DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("member_id", rs.getInt("member_id"));
                row.put("member_name", rs.getString("member_name"));
                row.put("total_spending", rs.getDouble("total_spending"));
                membersData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersData;
    }
    
}

