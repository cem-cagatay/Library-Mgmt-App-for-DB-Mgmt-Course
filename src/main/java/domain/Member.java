package domain;

public class Member {
    private int memberId;
    private String username;
    private String name;
    private String lastName;
    private String email;

    // Constructors
    public Member(int memberId, String name, String lastName, String email) {
        this.memberId = memberId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters and Setters
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getLastName() { return lastName; }
    public void setLastnameName(String lastname) { this.lastName = lastname; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}

