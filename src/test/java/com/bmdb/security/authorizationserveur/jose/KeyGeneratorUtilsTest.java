package com.bmdb.security.authorizationserveur.jose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.SecretKey;

/**
 * Test class for KeyGeneratorUtils
 */
public class KeyGeneratorUtilsTest {

    @Test
    public void testGenerateRsaKey() {
        // Test that RSA key generation works
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        
        // Verify the key pair is not null
        assertNotNull(keyPair, "Key pair should not be null");
        
        // Verify the public key is an RSA public key
        assertTrue(keyPair.getPublic() instanceof RSAPublicKey, 
                "Public key should be an instance of RSAPublicKey");
        
        // Verify the private key is an RSA private key
        assertTrue(keyPair.getPrivate() instanceof RSAPrivateKey, 
                "Private key should be an instance of RSAPrivateKey");
        
        // Verify the key size is 2048 bits
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        assertEquals(2048, publicKey.getModulus().bitLength(), 
                "RSA key should be 2048 bits");
        
        System.out.println("[DEBUG_LOG] RSA key pair generated successfully with key size: " 
                + publicKey.getModulus().bitLength() + " bits");
    }
    
    @Test
    public void testGenerateSecretKey() {
        // Test that secret key generation works
        SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
        
        // Verify the secret key is not null
        assertNotNull(secretKey, "Secret key should not be null");
        
        // Verify the algorithm is HmacSHA256
        assertEquals("HmacSHA256", secretKey.getAlgorithm(), 
                "Secret key algorithm should be HmacSHA256");
        
        System.out.println("[DEBUG_LOG] Secret key generated successfully with algorithm: " 
                + secretKey.getAlgorithm());
    }
}