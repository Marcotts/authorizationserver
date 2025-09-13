package com.bmdb.security.authorizationserveur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

    @Bean
    @Order(2) // Ordre inférieur à celui du serveur d'autorisation
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(resourceServerJwtDecoder()
                    )
                )
            );

        return http.build();
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder
//            .withJwkSetUri("http://localhost:9000/oauth2/jwks")
//            .build();
//    }


    @Bean("resourceServerJwtDecoder") // Nom différent pour éviter le conflit
    public JwtDecoder resourceServerJwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri("http://localhost:9000/oauth2/jwks")
                .build();
    }

}
