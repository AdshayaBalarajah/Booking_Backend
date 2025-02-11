package com.tech.repository;

import com.tech.entity.RefreshToken;
import com.tech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    // Delete refresh tokens associated with a specific user (useful on logout)
    void deleteByUser(User user);

    RefreshToken findByUser(User user);
}
