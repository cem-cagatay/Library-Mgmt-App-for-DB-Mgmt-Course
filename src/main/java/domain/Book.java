package domain;

public class Book {

    private String book_id;        
    private String author_id;     
    private int publish_year;     
    private String title;        
    private String subject;      
    private int totalCopies;       
    private int availableCopies;       


    public Book() {
    }


	public Book(String book_id, String author_id, int publish_year, String title, String subject, int totalCopies,
			int availableCopies) {
		super();
		this.book_id = book_id;
		this.author_id = author_id;
		this.publish_year = publish_year;
		this.title = title;
		this.subject = subject;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}


	public String getBook_id() {
		return book_id;
	}


	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}


	public String getAuthor_id() {
		return author_id;
	}


	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}


	public int getPublish_year() {
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

