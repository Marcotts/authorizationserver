package com.bmdb.security.authorizationserveur.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test") // Utilisez un profil de test si nécessaire
public class OAuth2ConfigTest {



    @Autowired
    private Environment environment;

    @Test
    public void testGoogleOAuth2ConfigProperties() {
        // Vérifier si les propriétés sont définies
        String clientId = environment.getProperty("spring.security.oauth2.client.registration.google.client-id");
        String clientSecret = environment.getProperty("spring.security.oauth2.client.registration.google.client-secret");

        // Assurez-vous que les propriétés ne sont pas nulles
        assertNotNull(clientId, "Le client-id Google devrait être configuré:"+ clientId);
        assertNotNull(clientSecret, "Le client-secret Google devrait être configuré"+clientSecret);

        // Vérifiez que les valeurs ne sont pas les valeurs par défaut du fichier de propriétés
        assertTrue(!clientId.equals("See Config Server"), "Le client-id n'a pas été remplacé par la valeur du Config Server");
        assertTrue(!clientSecret.equals("See Config Server"), "Le client-secret n'a pas été remplacé par la valeur du Config Server");

        // Afficher les valeurs pour vérification (uniquement en développement/test)
        System.out.println("Google OAuth2 client-id: " + clientId);
        System.out.println("Google OAuth2 client-secret: " + clientSecret);
    }
}
