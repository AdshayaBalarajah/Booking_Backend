package com.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long consultantId;
    private LocalDateTime appointmentDateTime;
    private String status; // PENDING, ACCEPTED, DECLINED, COMPLETED
    private String notes;
}
