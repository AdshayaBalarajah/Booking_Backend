package com.tech.service;

import com.tech.entity.ApiResponse;
import com.tech.entity.Booking;
import com.tech.entity.Status;  // Import Status enum
import com.tech.entity.User;
import com.tech.repository.BookingRepository;
import com.tech.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    // For demonstration, we use a fixed list of time slots.
    private final List<String> allTimeSlots = Arrays.asList(
            "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00"
    );

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Returns the list of available time slots for a given date.
     */
    public List<String> getAvailableSlots(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);

        // Retrieve bookings for the given date.
        List<Booking> bookingsForDate = bookingRepository.findAll().stream()
                .filter(booking -> booking.getAppointmentDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());

        // Extract the time slots that are already booked.
        Set<String> bookedSlots = bookingsForDate.stream()
                .map(Booking::getTimeSlot)
                .collect(Collectors.toSet());

        // Return only those slots that are not booked.
        return allTimeSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .collect(Collectors.toList());
    }

    public List<Booking> getAppointmentsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public ApiResponse cancelAppointment(Long id, User user) {
        Booking booking = bookingRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Booking not found or unauthorized access"));

        // Additional business rules may apply to allow cancellation only in certain states.
        bookingRepository.delete(booking);
        return new ApiResponse(true, "Appointment cancelled successfully");
    }

    /**
     * Books an appointment for the given user if the time slot is available.
     */
    public ApiResponse bookAppointment(BookingRequest bookingRequest, User user) {
        LocalDate date = LocalDate.parse(bookingRequest.getDate());
        String timeSlot = bookingRequest.getTimeSlot();

        // Get the start and end time for the time slot
        LocalDateTime startDateTime = date.atTime(Integer.parseInt(timeSlot.split(":")[0]), 0);
        LocalDateTime endDateTime = startDateTime.plusHours(1);  // Assuming each slot is 1 hour

        // Validate that the slot is not already booked
        if (bookingRepository.existsByAppointmentDateTimeBetween(startDateTime, endDateTime)) {
            return new ApiResponse(false, "Time slot already booked");
        }

        // Create and save the booking
        Booking booking = new Booking();
        booking.setAppointmentDateTime(startDateTime);  // Setting the correct LocalDateTime
        booking.setUser(user);
        booking.setConsultantName(bookingRequest.getConsultantName());
        booking.setStatus(Status.PENDING);  // Set to PENDING status using the enum
        booking.setUserNotes(bookingRequest.getUserNotes());
        booking.setPaymentDone(false); // Set to false initially, can be updated after payment

        bookingRepository.save(booking);

        return new ApiResponse(true, "Appointment booked successfully");
    }
}
