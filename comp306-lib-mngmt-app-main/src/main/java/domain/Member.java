package domain;

public class Member {
    private String memberId;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    
    private Address address;

    // Constructors
    public Member(String memberId, String username, String name, String email,
                  String phoneNumber, Address address) {
        this.memberId = memberId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getUsername() { return username; }
    public void setUsernameName(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}

