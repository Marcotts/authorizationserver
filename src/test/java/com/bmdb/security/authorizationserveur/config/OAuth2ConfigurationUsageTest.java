package com.bmdb.security.authorizationserveur.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.security.oauth2.client.registration.google.client-id=test-client-id",
        "spring.security.oauth2.client.registration.google.client-secret=test-client-secret",
        "spring.security.oauth2.client.registration.google.scope=openid,profile,email",
        "spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}",
        "spring.cloud.config.enabled=false"
})
public class OAuth2ConfigurationUsageTest {

    @Autowired(required = false)
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    public void testClientRegistrationIsConfigured() {
        // Si cette assertion échoue, cela signifie que la configuration OAuth2 n'est pas activée dans l'application
        assertNotNull(clientRegistrationRepository, "ClientRegistrationRepository devrait être configuré");

        // Vérifier que l'enregistrement Google existe et a les bonnes valeurs
        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
        assertNotNull(googleRegistration, "L'enregistrement client Google devrait exister");

        // Vérifier les propriétés spécifiques
        assertEquals("test-client-id", googleRegistration.getClientId());
        assertEquals("test-client-secret", googleRegistration.getClientSecret());
    }
}
