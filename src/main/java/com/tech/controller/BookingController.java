package com.tech.controller;

import com.tech.dto.BookingDTO;
import com.tech.entity.ApiResponse;
import com.tech.entity.Booking;
import com.tech.entity.User;
import com.tech.request.BookingRequest;
import com.tech.service.BookingService;
import com.tech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // GET /appointments/slots?date=YYYY-MM-DD
    // Retrieves available time slots for a given date.
    @GetMapping("/slots")
    public ResponseEntity<List<String>> getAvailableSlots(@RequestParam String date) {
        List<String> slots = bookingService.getAvailableSlots(date);
        return ResponseEntity.ok(slots);
    }

    // POST /appointments
    // Books an appointment based on the provided details.
    @PostMapping
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody BookingRequest bookingRequest) {
        // Use Principal to get the logged-in user's email.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("Current user is:"+ userName);
        User user = userService.getUserByEmail(userName);
        ApiResponse response = bookingService.bookAppointment(bookingRequest, user);
        return ResponseEntity.ok(response);
    }

    // GET /appointments
    // Retrieves the logged-in user's appointments.
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getUserAppointments(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Booking> bookings = bookingService.getAppointmentsByUser(user);

        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(this::convertToDTO)
                .toList();

        return ResponseEntity.ok(bookingDTOs);
    }

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                booking.getConsultantName(),
                booking.getAppointmentDateTime(),
                booking.getStatus().name(),
                booking.getUserNotes()
        );
    }


    // DELETE /appointments/{id}
    // Cancels an appointment if it belongs to the logged-in user.
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        ApiResponse response = bookingService.cancelAppointment(id, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Secured end point.");
    }
}