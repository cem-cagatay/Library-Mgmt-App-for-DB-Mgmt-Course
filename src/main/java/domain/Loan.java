package domain;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private String memberId; // Many-to-One relationship
    private int copy_id; // Many-to-One relationship
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    // Constructors
    public Loan(String loanId, String memberId, int copy_id, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.copy_id = copy_id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    

    public int getCopy_id() {
		return copy_id;
	}
	public void setCopy_id(int copy_id) {
		this.copy_id = copy_id;
	}

	public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    // Method to check if the loan is overdue
    public boolean isOverdue() {
        return (returnDate == null) && LocalDate.now().isAfter(dueDate);
    }
}
