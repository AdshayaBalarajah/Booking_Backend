package com.tech.repository;

import com.tech.entity.Booking;
import com.tech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Retrieve all bookings for a specific user
    List<Booking> findByUser(User user);

    // Retrieve a booking by its id and user (to validate ownership when canceling)
    Optional<Booking> findByIdAndUser(Long id, User user);

    // Validation: Check if a given time slot on a date is already booked
    boolean existsByAppointmentDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}