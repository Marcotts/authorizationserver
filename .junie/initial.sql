INSERT INTO PUBLIC.OAUTH2_REGISTERED_CLIENT (
    ID,
    CLIENT_ID,
    CLIENT_ID_ISSUED_AT,
    CLIENT_SECRET,
    CLIENT_SECRET_EXPIRES_AT,
    CLIENT_NAME,
    CLIENT_AUTHENTICATION_METHODS,
    AUTHORIZATION_GRANT_TYPES,
    REDIRECT_URIS,
    POST_LOGOUT_REDIRECT_URIS,
    SCOPES,
    CLIENT_SETTINGS,
    TOKEN_SETTINGS
) VALUES (
             'f1e2d3c4-b5a6-7890-1234-567890abcdef',
             'messaging-client',
             CURRENT_TIMESTAMP,
             '{noop}secret',
             NULL,
             'Messaging Client Application',
             'client_secret_basic',
             'authorization_code,refresh_token,client_credentials',
             'http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc,http://127.0.0.1:8080/authorized',
             'http://127.0.0.1:8080/logged-out',
             'openid,profile,message.read,message.write,user.read',
             '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}',
             '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
         );
