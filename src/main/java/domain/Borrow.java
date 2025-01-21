package domain;

import java.time.LocalDate;

public class Borrow {
    private Member member; // Many-to-One relationship
    private BookCopy bookCopy; // Many-to-One relationship
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Borrow(Member member, BookCopy bookCopy, LocalDate borrowDate, LocalDate dueDate) {
        this.member = member;
        this.bookCopy = bookCopy;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public BookCopy getCopy() {
		return bookCopy;
	}
	public void setCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDate getBorrowDate() { return borrowDate; }
	
    public void setLoanDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    // method to check if the borrow is overdue
    public boolean isOverdue() {
        return (returnDate == null) && LocalDate.now().isAfter(dueDate);
    }
}
