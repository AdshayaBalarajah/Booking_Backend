package com.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String consultantName;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;  // PENDING, ACCEPTED, DECLINED, COMPLETED

    @Column(nullable = false)
    private String userNotes;

    @Column(nullable = false)
    private boolean paymentDone;

    // Method to return the time slot as a string (e.g., "14:00")
    public String getTimeSlot() {
        return this.appointmentDateTime.getHour() + ":00"; // Returns only the hour, e.g., "14:00"
    }


//    @ManyToOne
//    @JoinColumn(name = "admin_id")
//    private Admin admin; // Admin who manages the appointment

    // Manually add setters for properties in case Lombok isn't generating them
    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public User getUser() {
        return this.user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }
}