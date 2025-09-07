package com.bmdb.security.authorizationserveur.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class OAuth2LoginController {

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        // Afficher les informations de l'utilisateur connecté
        System.out.println("Utilisateur connecté : " + principal.getAttributes());
        return "user";
    }
}
