package com.bmdb.security.authorizationserveur.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.security.oauth2.client.registration.google.client-id=test-id-value",
        "spring.security.oauth2.client.registration.google.client-secret=test-secret-value",
        "spring.cloud.config.enabled=false"
})
public class OAuth2PropertySourceTest {

    @Value("${spring.security.oauth2.client.registration.google.client-id:not-set}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret:not-set}")
    private String googleClientSecret;

    @Test
    public void testOAuth2PropertiesAreSet() {
        // Vérifier que les propriétés sont injectées correctement
        assertNotNull(googleClientId);
        assertNotNull(googleClientSecret);

        // Vérifier les valeurs spécifiques
        assertEquals("test-id-value", googleClientId);
        assertEquals("test-secret-value", googleClientSecret);
    }
}
