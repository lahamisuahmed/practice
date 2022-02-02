package com.practice.util;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Component
public class JoseTokenEncryptionUtil implements TokenEncryptionUtil{

    final ResourceLoader resourceLoader;
    final ApplicationContext applicationContext;

    public JoseTokenEncryptionUtil(ResourceLoader resourceLoader, ApplicationContext applicationContext) {
        this.resourceLoader = resourceLoader;
        this.applicationContext = applicationContext;
    }

    private RSAPrivateKey getPrivateKey() {
        try {
            ClassPathResource resource = new ClassPathResource("private_key.pem");

            return (RSAPrivateKey) PemUtils.readPrivateKeyFromInputStream(resource.getInputStream(), "RSA");
        } catch (IOException e) {
            return null;
        }
    }

    private RSAPublicKey getPublicKey() {
        try {
            ClassPathResource resource = new ClassPathResource("public_key.pem");

            return (RSAPublicKey) PemUtils.readPublicKeyFromInputStream(resource.getInputStream(), "RSA");
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> String encryptToken(T claimsSet) throws JOSEException {
        // Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

        // Create the encrypted JWT object
        EncryptedJWT jwt = new EncryptedJWT(header, (JWTClaimsSet) claimsSet);

        // Create an encrypter with the specified public RSA key
        RSAEncrypter encrypter = new RSAEncrypter(getPublicKey());

        // Do the actual encryption
        jwt.encrypt(encrypter);

        // Serialise to JWT compact form and return
        return  jwt.serialize();
    }

    @Override
    public EncryptedJWT decryptToken(String jwt) throws Exception {
        // create the encrypted JWT with the base64-encoded text.
        EncryptedJWT encryptedJWT = EncryptedJWT.parse(jwt);

        // Create a decrypter with the specified private RSA key
        RSADecrypter decrypter = new RSADecrypter(getPrivateKey());

        // decrypt the JWT.
        encryptedJWT.decrypt(decrypter);

        return encryptedJWT;
    }
}
