package domain;

import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private String memberId; // Many-to-One relationship
    private String bookIsbn; // Many-to-One relationship
    private LocalDate reservationDate;
    private boolean isActive;

    // Constructors
    public Reservation(String reservationId, String memberId, String bookIsbn, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.bookIsbn = bookIsbn;
        this.reservationDate = reservationDate;
        this.isActive = true;
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // Method to cancel reservation
    public void cancelReservation() {
        this.isActive = false;
    }
}

