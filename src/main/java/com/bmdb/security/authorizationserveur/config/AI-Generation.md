Cette classe **`AuthorizationServerConfig`** est une configuration Spring Boot qui implémente un **serveur d'autorisation OAuth 2.0/OpenID Connect** complet. Voici une explication détaillée de ses composants principaux :
## Vue d'ensemble
Il s'agit d'une classe de configuration Spring (`@Configuration`) qui configure un serveur d'autorisation OAuth 2.0 avec support pour plusieurs flux d'authentification et utilise une base de données embarquée H2 pour la persistance.
## Composants principaux
### 1. **Chaîne de filtre de sécurité** (`authorizationServerSecurityFilterChain`)
- Configure les endpoints OAuth 2.0 (autorisation, token, etc.)
- **Particularité** : Supporte l'authentification de **clients d'appareils** (Device Flow) sans authentification traditionnelle
- Définit une page de consentement personnalisée (`/oauth2/consent`)
- Active OpenID Connect 1.0

### 2. **Clients OAuth 2.0 enregistrés** (`registeredClientRepository`)
Configure trois types de clients :
**Client de messagerie** :
``` java
- clientId: "messaging-client"
- Flux supportés: Authorization Code, Refresh Token, Client Credentials
- Nécessite consentement utilisateur
```
**Client d'appareil** :
``` java
- clientId: "device-messaging-client" 
- Flux: Device Code (pour appareils sans navigateur)
- Aucune authentification requise (ClientAuthenticationMethod.NONE)
```
**Client d'échange de token** :
``` java
- clientId: "token-client"
- Flux: Token Exchange (RFC 8693)
```
### 3. **Services de persistance**
- **`authorizationService`** : Stocke les autorisations OAuth 2.0
- **`authorizationConsentService`** : Gère les consentements utilisateur
- **Base de données embarquée H2** avec schémas OAuth 2.0

### 4. **Configuration JWT/JWK**
- **`jwkSource`** : Génère les clés RSA pour signer les tokens JWT
- **`jwtDecoder`** : Décode et valide les tokens JWT
- **`idTokenCustomizer`** : Personnalise les ID tokens OpenID Connect

### 5. **Configuration spécialisée Device Flow**
La classe utilise des composants personnalisés pour le flux d'appareils :
- **`DeviceClientAuthenticationConverter`** : Convertit les requêtes d'authentification
- **`DeviceClientAuthenticationProvider`** : Valide les clients d'appareils

## Points techniques importants
### Sécurité
``` java
// ATTENTION: Les endpoints d'appareils ne nécessitent pas d'authentification
// Recommandation: Surveillance et protections supplémentaires nécessaires
```
### Configuration de base
- **Port HTTP** : 9000 (via ) `TomcatServerConfig`
- **Base de données** : H2 embarquée avec schémas OAuth 2.0
- **Encodage des secrets** : `{noop}` (pas de chiffrement - pour développement uniquement)

## Flux OAuth 2.0 supportés
1. **Authorization Code** - Applications web classiques
2. **Device Code** - Appareils IoT/TV sans navigateur
3. **Client Credentials** - Communication service-à-service
4. **Token Exchange** - Échange de tokens entre services
5. **Refresh Token** - Renouvellement des tokens

Cette configuration représente un serveur d'autorisation moderne et complet, particulièrement adapté aux architectures microservices et aux écosystèmes d'appareils connectés.
