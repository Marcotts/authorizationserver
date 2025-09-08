Cette classe est un contrôleur Spring MVC qui gère la **page de consentement OAuth2** dans un serveur d'autorisation. Voici son rôle et son fonctionnement : `AuthorizationConsentController`
## Vue d'ensemble
C'est un contrôleur qui permet aux utilisateurs de **donner leur consentement** pour qu'une application cliente puisse accéder à certaines de leurs données via OAuth2/OpenID Connect.
## Structure et composants principaux
### Dépendances injectées
- **`RegisteredClientRepository`** : pour récupérer les informations du client OAuth2
- **`OAuth2AuthorizationConsentService`** : pour gérer les consentements précédemment accordés

### Méthode principale : `consent()`
Cette méthode traite la requête GET sur `/oauth2/consent` et :
1. **Récupère les paramètres OAuth2** :
    - `clientId` : identifiant de l'application cliente
    - `scope` : permissions demandées
    - `state` : paramètre de sécurité OAuth2
    - `userCode` : optionnel, pour le flux Device Authorization

2. **Analyse les permissions** :
    - Sépare les permissions déjà accordées des nouvelles permissions
    - Ignore automatiquement le scope `openid` (scope obligatoire)
    - Divise en deux catégories :
        - `scopesToApprove` : nouvelles permissions à approuver
        - `previouslyApprovedScopes` : permissions déjà accordées

3. **Prépare le modèle** pour la vue :
``` java
   model.addAttribute("clientId", clientId);
   model.addAttribute("scopes", withDescription(scopesToApprove));
   model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
   model.addAttribute("principalName", principal.getName());
```
1. **Détermine l'URI de retour** :
    - `/oauth2/device_verification` si c'est un flux Device Authorization
    - `/oauth2/authorize` sinon

### Classe interne : `ScopeWithDescription`
Cette classe enrichit chaque permission avec une **description lisible** :
``` java
static {
    scopeDescriptions.put("message.read", "This application will be able to read your message.");
    scopeDescriptions.put("message.write", "This application will be able to add new messages...");
    // etc.
}
```
## Flux de fonctionnement
1. L'utilisateur arrive sur la page de consentement
2. Le contrôleur vérifie quelles permissions sont demandées
3. Il compare avec les consentements déjà accordés
4. Il affiche une page avec :
    - Les nouvelles permissions à approuver (cases à cocher)
    - Les permissions déjà accordées (cases cochées et désactivées)

5. L'utilisateur peut approuver ou annuler

## Intégration avec la vue
Le contrôleur retourne la vue `"consent"` qui correspond au template Thymeleaf . Cette page affiche une interface utilisateur permettant de : `consent.html`
- Voir les permissions demandées avec leurs descriptions
- Cocher les permissions à accorder
- Soumettre le consentement ou l'annuler

Cette classe est donc un élément central du processus d'autorisation OAuth2, garantissant que les utilisateurs donnent explicitement leur consentement avant qu'une application puisse accéder à leurs données.
