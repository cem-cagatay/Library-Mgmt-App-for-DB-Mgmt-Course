package domain;

public class Book {

    private String book_id;        
    private Author author;     
    private int publish_year;     
    private String title;        
    private String subject;      
    private int totalCopies;       
    private int availableCopies;       


    public Book() {
    }


	public Book(String book_id, Author author, int publish_year, String title, String subject) {
		super();
		this.book_id = book_id;
		this.author = author;
		this.publish_year = publish_year;
		this.title = title;
		this.subject = subject;
	}


	public String getBookId() {
		return book_id;
	}


	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}


	public Author getAuthor() {
		return author;
	}


	public void setAuthor(Author author) {
		this.author = author;
	}


	public int getPublishYear() {
		return publish_year;
	}


	public void setPublish_year(int publish_year) {
		this.publish_year = publish_year;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public int getTotalCopies() {
		return totalCopies;
	}


	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}


	public int getAvailableCopies() {
		return availableCopies;
	}


	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}
    
}

