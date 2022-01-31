package com.practice.repository;

import com.practice.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository <RefreshToken, String> {
    boolean existsByToken(String token);
}
