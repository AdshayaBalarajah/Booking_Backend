package com.tech.service;

import com.tech.entity.ApiResponse;
import com.tech.entity.Booking;
import com.tech.entity.Notification;
import com.tech.entity.Status;
import com.tech.repository.BookingRepository;
import com.tech.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final BookingRepository bookingRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public AdminService(BookingRepository bookingRepository,
                        NotificationRepository notificationRepository) {
        this.bookingRepository = bookingRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Retrieves all appointments (for admin dashboard use).
     */
    public List<Booking> getAllAppointments() {
        return bookingRepository.findAll();
    }

    public ApiResponse updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Convert the String status to the Status enum
        Status bookingStatus;
        try {
            bookingStatus = Status.valueOf(status); // Convert string to Status enum
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        booking.setStatus(bookingStatus);  // Use Status enum instead of String
        bookingRepository.save(booking);

        // Create and save a notification for the user
        Notification notification = new Notification(booking.getUser(), "Your appointment status has been updated to: " + status);
        notificationRepository.save(notification);

        return new ApiResponse(true, "Booking status updated successfully");
    }

    /**
     * Adds a time slot for appointments on a given date.
     * <p>
     * Note: In this example, available time slots are managed via a fixed list in BookingService.
     * If you implement time slot management as a separate feature, you may create a TimeSlot entity/repository.
     */
    public ApiResponse addTimeSlot(String dateStr, String timeSlot) {
        // Implement time slot addition logic if storing slots in the database.
        return new ApiResponse(true, "Time slot added successfully for date " + dateStr);
    }

    /**
     * Removes a time slot from the available list.
     */
    public ApiResponse removeTimeSlot(String dateStr, String timeSlot) {
        // Implement time slot removal logic if storing slots in the database.
        return new ApiResponse(true, "Time slot removed successfully for date " + dateStr);
    }

    public ApiResponse cancelAppointment(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Appointment not found"));
        bookingRepository.delete(booking);

        return new ApiResponse(true, "Appointment cancelled successfully");
    }
}
