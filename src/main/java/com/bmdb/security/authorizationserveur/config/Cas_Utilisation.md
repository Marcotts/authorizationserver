Bonjour ! Pour récupérer un JWT Bearer token avec cette application, vous avez plusieurs options selon le type de client que vous utilisez. Voici les méthodes principales :
## 1. Flux Authorization Code (Applications web)
Pour le client `messaging-client` :
``` bash
# 1. Rediriger l'utilisateur vers l'endpoint d'autorisation
GET http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&scope=openid%20message.read%20message.write

# 2. Après consentement, échanger le code contre un token
POST http://localhost:9000/oauth2/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=

grant_type=authorization_code&code=AUTHORIZATION_CODE&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc
```
## 2. Flux Client Credentials (Service-à-service)
Pour le client `messaging-client` en mode service :
``` bash
POST http://localhost:9000/oauth2/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=

grant_type=client_credentials&scope=message.read message.write
```
## 3. Flux Device Code (Appareils IoT/TV)
Pour le client `device-messaging-client` :
``` bash
# 1. Initier le flux d'appareil
POST http://localhost:9000/oauth2/device_authorization
Content-Type: application/x-www-form-urlencoded

client_id=device-messaging-client&scope=message.read message.write

# 2. Récupérer le token avec le device_code
POST http://localhost:9000/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=urn:ietf:params:oauth:grant-type:device_code&device_code=DEVICE_CODE&client_id=device-messaging-client
```
## 4. Utilisation du token JWT
Une fois que vous avez obtenu le token, utilisez-le dans vos requêtes :
``` bash
GET http://your-resource-server/api/messages
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```
## Exemple avec curl
``` bash
# Obtenir un token via Client Credentials
TOKEN_RESPONSE=$(curl -X POST http://localhost:9000/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -u "messaging-client:secret" \
  -d "grant_type=client_credentials&scope=message.read message.write")

# Extraire le token
ACCESS_TOKEN=$(echo $TOKEN_RESPONSE | jq -r '.access_token')

# Utiliser le token
curl -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://your-api-endpoint/api/resource
```
Le serveur d'autorisation fonctionne sur le **port 9000** selon votre configuration. Choisissez le flux approprié selon votre type d'application (web, mobile, IoT, ou service backend).
