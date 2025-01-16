package domain;

import java.util.ArrayList;

public class Publisher {
    private String publisherId;
    private String name;
    //address can be deleted 
    private String address;
    public ArrayList<Book> books;

    // Constructors
    public Publisher(String publisherId, String name, String address) {
        this.publisherId = publisherId;
        this.name = name;
        this.address = address;
    }

    // Getters and Setters
    public String getPublisherId() { return publisherId; }
    public void setPublisherId(String publisherId) { this.publisherId = publisherId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
