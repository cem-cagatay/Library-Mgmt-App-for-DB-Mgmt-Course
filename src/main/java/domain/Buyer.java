package domain;

public class Buyer extends Member {
    
    private String billing_address;
    
    // Constructor
    public Buyer(int member_id, 
                 String firstname, 
                 String lastName, 
                 String email,  
                 String billing_address) {
        super(member_id, firstname, lastName, email);
        this.billing_address = billing_address;
    }

    // Getter Setter
    public String getBillingAddress() {
        return billing_address;
    }

    public void setBillingAddress(String billing_address) {
        this.billing_address = billing_address;
    }
}
