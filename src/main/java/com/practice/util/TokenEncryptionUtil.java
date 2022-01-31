package com.practice.util;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Component;

@Component
public interface TokenEncryptionUtil {
    <T> T encryptToken(JWTClaimsSet claimsSet) throws Exception;
    <T> T decryptToken(String jwt) throws Exception;
}
