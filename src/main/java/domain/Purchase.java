package domain;

public class Purchase {

    private Member member; 
    private BookCopy bookCopy;

    public Purchase(Member member, BookCopy bookCopy) {
        this.member = member;
        this.bookCopy = bookCopy;
    }
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }
    
}