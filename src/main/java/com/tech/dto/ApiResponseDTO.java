package com.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {
    private boolean success;
    private String message;
    private Object data; // Can hold any type of response data
}
