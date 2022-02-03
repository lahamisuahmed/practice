package com.practice.util;

import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.practice.model.AccessToken;
import com.practice.model.RefreshToken;
import com.practice.model.Role;
import com.practice.model.User;
import com.practice.repository.AccessTokenRepository;
import com.practice.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.practice.constants.AppConstants.*;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${security.config.issuer-id}")
    private String issuerId;

    @Value("${security.config.access-token-lifespan}")
    private int accessTokenLifespan;

    @Value("${security.config.refresh-token-lifespan}")
    private int refreshTokenLifespan;

    private final List<String> audience = Collections.singletonList("http://localhost:8000");

    TokenEncrypter tokenEncrypter;
    AccessTokenRepository accessTokenRepository;
    RefreshTokenRepository refreshTokenRepository;

    public JwtTokenUtil(TokenEncrypter tokenEncrypter,
                        AccessTokenRepository accessTokenRepository,
                        RefreshTokenRepository refreshTokenRepository) {
        this.tokenEncrypter = tokenEncrypter;
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Map<String, String> generateToken( UserDetails userDetails) throws Exception {

        Date issueTime = DateUtil.getCurrentSqlDatetime();
        Date accessTokenExpirationTime = DateUtil.addSecondsToDate(accessTokenLifespan);
        Date refreshTokenExpirationTime = DateUtil.addSecondsToDate(refreshTokenLifespan);

        JWTClaimsSet accessTokenClaims = getJwtClaimsSet(userDetails, issueTime, accessTokenExpirationTime);
        JWTClaimsSet refreshTokenClaims = getJwtClaimsSet(userDetails, issueTime, refreshTokenExpirationTime);

        String access_token = tokenEncrypter.encryptToken(accessTokenClaims);
        String refresh_token = tokenEncrypter.encryptToken(refreshTokenClaims);

        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, access_token);
        tokens.put(REFRESH_TOKEN, refresh_token);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refresh_token);
        refreshToken.setCreatedAt(DateUtil.convertToLocalDateViaInstant(issueTime));
        refreshToken.setExpiryDate(DateUtil.convertToLocalDateViaInstant(accessTokenExpirationTime));


        AccessToken accessToken = new AccessToken();
        accessToken.setToken(access_token);
        accessToken.setRefreshToken(refreshToken);
        accessToken.setUser((User) userDetails);
        accessToken.setCreatedAt(DateUtil.convertToLocalDateViaInstant(issueTime));
        accessToken.setExpiryDate(DateUtil.convertToLocalDateViaInstant(accessTokenExpirationTime));

        accessTokenRepository.save(accessToken);
        refreshTokenRepository.save(refreshToken);

        return tokens;
    }

    private JWTClaimsSet getJwtClaimsSet(UserDetails userDetails, Date issueTime, Date accessTokenExpirationTime) {
        return new JWTClaimsSet.Builder()
                .issuer(issuerId)
                .subject(String.valueOf(userDetails.getUsername()))
                .audience(audience)
                .issueTime(issueTime)
                .expirationTime(accessTokenExpirationTime)
                .claim(ROLES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .jwtID(UUID.randomUUID().toString())
                .build();
    }

    public UserDetails getUserDetailsFromToken(String token) throws Exception {

        EncryptedJWT encryptedJWT = tokenEncrypter.decryptToken(token);

        JWTClaimsSet jwtClaimsSet = encryptedJWT.getJWTClaimsSet();

        Set<Role> userRoles = jwtClaimsSet.getStringListClaim(ROLES).stream().map(Role::new).collect(Collectors.toSet());

        User user = new User();
        user.setId(Long.parseLong(jwtClaimsSet.getSubject()));
        user.setRoles(userRoles);

        return user;
    }

    public Boolean isValidToken(String token) {

        if(!accessTokenRepository.existsByToken(token))
            return refreshTokenRepository.existsByToken(token);
        else{
            return true;
        }
    }

    public  boolean isTokenExpired(String token) throws Exception {
        EncryptedJWT encryptedJWT = tokenEncrypter.decryptToken(token);

        LocalDateTime expirationTime = DateUtil.convertToLocalDateTime(encryptedJWT.getJWTClaimsSet().getExpirationTime());
        LocalDateTime currentTime = DateUtil.getCurrentSqlDatetime().toLocalDateTime();

        return currentTime.isAfter(expirationTime);
    }

}
