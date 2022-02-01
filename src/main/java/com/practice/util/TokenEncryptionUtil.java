package com.practice.util;

import org.springframework.stereotype.Component;

@Component
public interface TokenEncryptionUtil {
    <T> String encryptToken(T claimsSet) throws Exception;
    <T> T decryptToken(String jwt) throws Exception;
}
