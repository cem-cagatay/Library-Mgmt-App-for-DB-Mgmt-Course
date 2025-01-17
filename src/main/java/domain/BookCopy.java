package domain;

import java.math.BigDecimal;

public class BookCopy {
	private int copy_id;
	private String book_id;
    private String status;     
    private BigDecimal price;
    private int floor_number; 
    private String shelf_latter;      
    private int shelf_number;
    
    
	public BookCopy(int copy_id, String book_id, String status, BigDecimal price, int floor_number, String shelf_latter,
			int shelf_number) {
		super();
		this.copy_id = copy_id;
		this.book_id = book_id;
		this.status = status;
		this.price = price;
		this.floor_number = floor_number;
		this.shelf_latter = shelf_latter;
		this.shelf_number = shelf_number;
	}


	public int getCopy_id() {
		return copy_id;
	}


	public void setCopy_id(int copy_id) {
		this.copy_id = copy_id;
	}


	public String getBook_id() {
		return book_id;
	}


	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public int getFloor_number() {
		return floor_number;
	}


	public void setFloor_number(int floor_number) {
		this.floor_number = floor_number;
	}


	public String getShelf_latter() {
		return shelf_latter;
	}


	public void setShelf_latter(String shelf_latter) {
		this.shelf_latter = shelf_latter;
	}


	public int getShelf_number() {
		return shelf_number;
	}


	public void setShelf_number(int shelf_number) {
		this.shelf_number = shelf_number;
	}       
    
	
    


}
