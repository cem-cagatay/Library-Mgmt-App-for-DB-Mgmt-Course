package domain;

import java.math.BigDecimal;

public class Borrower extends Member {
    
    private BigDecimal total_fines_due;
    
    public Borrower(int member_id, String firstname, String lastName, String email, BigDecimal total_fines_due) {
        super(member_id, firstname, lastName, email);
        this.total_fines_due = total_fines_due;
    }

    public BigDecimal getTotalFinesDue() {
        return total_fines_due;
    }

    public void setTotalFinesDue(BigDecimal total_fines_due) {
        this.total_fines_due = total_fines_due;
    }
}
