package com.practice.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_token")
public class AccessToken {

    @Id
    private String token;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "refresh_token_id", referencedColumnName = "token")
    private RefreshToken refreshToken;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @Column(name ="expiry_date")
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
