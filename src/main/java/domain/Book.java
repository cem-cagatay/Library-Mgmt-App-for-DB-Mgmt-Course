package domain;
public class Book {
    private String isbn;
    private String title;
    private int publicationYear;
    private String category;     // Category as String
    private Author author;     // Reference to Author
    private int totalCopies;
    private int availableCopies;
    

    // Constructors
    public Book(String isbn, String title, int publicationYear,
                String category, String authorId, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.category = category;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

  

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { 
        this.totalCopies = totalCopies; 
        if(this.availableCopies > totalCopies){
            this.availableCopies = totalCopies;
        }
    }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    // Methods to manage copies
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void borrowCopy() {
        if (isAvailable()) {
            availableCopies--;
        } else {
            throw new IllegalStateException("No available copies to borrow.");
        }
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        } else {
            throw new IllegalStateException("All copies are already in the library.");
        }
    }
}
