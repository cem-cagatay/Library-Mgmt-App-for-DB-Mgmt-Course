package domain;
import java.util.ArrayList;

public class Author {
    private String authorId;
    private String name;
    public ArrayList<Book> books;

    // Constructors
    public Author(String authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.name = firstName;
        this.books = books;
    }

    // Getters and Setters
    public String getAuthorId() {
        return authorId; }
    public void setAuthorId(String authorId) { 
        this.authorId = authorId; }

    public String getName() { 
        return name; }
    public void setName(String firstName) { 
        this.name = firstName; }

    public ArrayList<Book> getBooks() {
        return books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

   
}
