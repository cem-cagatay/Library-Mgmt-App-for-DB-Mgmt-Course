package domain;

import java.util.List;

import database.DatabaseHandler;

public class Member {
    private int memberId;
    private String firstname;
    private String lastName;
    private String email;
    private String creditCardNumber; 
    private String billingAddress; // buyer attribute
    private List<BookCopy> borrowedBooks; // borrower attribute
    private List<BookCopy> purchasedBooks; // borrower attribute
    private boolean isBorrowedBooksCacheValid = false; // Cache validation flag
    private boolean isPurchasedBooksCacheValid = false; // Cache validation flag
    
	public Member(int memberId, String firstname, String lastName, String email) {
		super();
		this.memberId = memberId;
		this.firstname = firstname;
		this.lastName = lastName;
		this.email = email;
		
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public List<BookCopy> getBorrowedBooks() {
        if (!isBorrowedBooksCacheValid) {
            borrowedBooks = DatabaseHandler.getBorrowedBooks(this);
            isBorrowedBooksCacheValid = true;
        }
        return borrowedBooks;
    }

    public List<BookCopy> getPurchasedBooks() {
        if (!isPurchasedBooksCacheValid) {
            purchasedBooks = DatabaseHandler.getPurchasedBooks(this);
            isPurchasedBooksCacheValid = true;
        }
        return purchasedBooks;
    }

    public void invalidateBorrowedBooksCache() {
        isBorrowedBooksCacheValid = false;
    }

    public void invalidatePurchasedBooksCache() {
        isPurchasedBooksCacheValid = false;
    }

    public void borrowBook(BookCopy bookCopy) {
        borrowedBooks.add(bookCopy);
    }

    public void returnBook(BookCopy bookCopy) {
        borrowedBooks.remove(bookCopy);
    }

}

