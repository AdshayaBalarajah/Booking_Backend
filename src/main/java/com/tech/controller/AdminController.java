package com.tech.controller;

import com.tech.entity.ApiResponse;
import com.tech.entity.Booking;
import com.tech.request.BookingStatusUpdateRequest;
import com.tech.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // GET /admin/appointments
    // Retrieves all appointments for the admin dashboard.
    @GetMapping("/appointments")
    public ResponseEntity<List<Booking>> getAllAppointments() {
        List<Booking> bookings = adminService.getAllAppointments();
        return ResponseEntity.ok(bookings);
    }

    // PUT /admin/appointments/{id}/status
    // Allows admin to update the status of an appointment.
    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<ApiResponse> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody BookingStatusUpdateRequest statusUpdateRequest) {
        ApiResponse response = adminService.updateBookingStatus(id, statusUpdateRequest.getStatus());
        return ResponseEntity.ok(response);
    }

    // Optional: Manage time slots
    // POST /admin/slots?date=YYYY-MM-DD&timeSlot=HH:mm
    @PostMapping("/slots")
    public ResponseEntity<ApiResponse> addTimeSlot(@RequestParam String date, @RequestParam String timeSlot) {
        ApiResponse response = adminService.addTimeSlot(date, timeSlot);
        return ResponseEntity.ok(response);
    }

    // DELETE /admin/slots?date=YYYY-MM-DD&timeSlot=HH:mm
    @DeleteMapping("/slots")
    public ResponseEntity<ApiResponse> removeTimeSlot(@RequestParam String date, @RequestParam String timeSlot) {
        ApiResponse response = adminService.removeTimeSlot(date, timeSlot);
        return ResponseEntity.ok(response);
    }
}
