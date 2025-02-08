package com.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {
    private String email;
    private String newPassword;
    private String confirmPassword;
    private String token; // Reset token for verification
}
