package domain;
import java.util.ArrayList;

public class Author {
    private String author_id;
    private String first_name;
    private String last_name;
    public ArrayList<Book> books;

    // Constructors
    public Author(String author_id, String first_name, String last_name) {
        this.author_id = author_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.books = books;
    }

    // Getters and Setters
    public String getAuthorId() {
        return author_id; }
    public void setAuthorId(String author_id) { 
        this.author_id = author_id; }



    public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public ArrayList<Book> getBooks() {
        return books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

   
}
