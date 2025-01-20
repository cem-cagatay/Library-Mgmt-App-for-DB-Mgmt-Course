package domain;

public class BookCopy {
	private int copy_id;
	private Book book;
    private String status;     
    private double price;
    private int floor_number; 
    private char shelf_letter;      
    private int shelf_number;
    
    
	public BookCopy(int copy_id, Book book, String status, double price, int floor_number, char shelf_letter,
			int shelf_number) {
		super();
		this.copy_id = copy_id;
		this.book = book;
		this.status = status;
		this.price = price;
		this.floor_number = floor_number;
		this.shelf_letter = shelf_letter;
		this.shelf_number = shelf_number;
	}


	public int getCopyId() {
		return copy_id;
	}


	public void setCopyId(int copy_id) {
		this.copy_id = copy_id;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getFloorNumber() {
		return floor_number;
	}


	public void setFloorNumber(int floor_number) {
		this.floor_number = floor_number;
	}


	public char getShelfLetter() {
		return shelf_letter;
	}


	public void setShelfLetter(char shelf_letter) {
		this.shelf_letter = shelf_letter;
	}


	public int getShelfNumber() {
		return shelf_number;
	}


	public void setShelfNumber(int shelf_number) {
		this.shelf_number = shelf_number;
	}       
    
	
    


}
