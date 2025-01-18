package domain;

public class Member {
    private int member_id;
    private String firstname;
    private String lastName;
    private String email;
   
    
	public Member(int member_id, String firstname, String lastName, String email) {
		super();
		this.member_id = member_id;
		this.firstname = firstname;
		this.lastName = lastName;
		this.email = email;
		
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
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
    
    

}

