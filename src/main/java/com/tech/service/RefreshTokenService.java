package com.tech.service;

import com.tech.entity.RefreshToken;
import com.tech.entity.User;
import com.tech.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken createRefreshToken(User user){


        RefreshToken refreshToken = refreshTokenRepository.findByUser(user);

        if(refreshToken == null){
            // Generate a refresh token
            refreshToken = new RefreshToken();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));  // for example, expires in 7 days
            refreshTokenRepository.save(refreshToken);
        }
        return  refreshToken;
    }
}
