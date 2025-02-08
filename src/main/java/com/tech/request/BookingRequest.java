package com.tech.request;

public class BookingRequest {

    private String date;
    private String timeSlot;
    private Long userId;
    private String consultantName; // Added this field
    private String userNotes;  // Added userNotes field

    // Constructors, getters, and setters

    public BookingRequest() {}

    public BookingRequest(String date, String timeSlot, Long userId, String consultantName, String userNotes) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.userId = userId;
        this.consultantName = consultantName;
        this.userNotes = userNotes;  // Initialize userNotes
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConsultantName() {
        return consultantName;  // Getter for consultant's name
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;  // Setter for consultant's name
    }

    public String getUserNotes() {
        return userNotes;  // Getter for userNotes
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;  // Setter for userNotes
    }
}
