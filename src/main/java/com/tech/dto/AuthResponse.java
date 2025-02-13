package com.tech.dto;

import com.tech.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private Long userid;
    private UserRole userRole;
    private String accessToken;
    private String refreshToken;
}
