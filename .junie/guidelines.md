# Authorization Server Development Guidelines

This document provides guidelines for developing and working with the Authorization Server project.

## Build/Configuration Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Building the Project
1. Clone the repository
2. Navigate to the project root directory
3. Run the following command to build the project:
   ```
   mvnw clean install
   ```

### Running the Application
1. After building, run the application using:
   ```
   mvnw spring-boot:run
   ```
2. The authorization server will be available at `http://localhost:9005`

### Configuration
The application uses Spring Boot's configuration system. Key configuration files:

1. **application.properties**: Contains basic configuration settings
   - OAuth2 client registration details
   - You can add custom properties like server port, logging levels, etc.

2. **AuthorizationServerConfig.java**: Contains OAuth2 server configuration
   - Registered clients
   - Token settings
   - Endpoint configurations

3. **DefaultSecurityConfig.java**: Contains web security configuration
   - Authentication providers
   - Security filter chains
   - Login page configuration

### Important Configuration Properties
- `spring.security.oauth2.client.registration.*`: OAuth2 client registration details
- Add custom database configuration if not using the embedded H2 database

## Testing Information

### Running Tests
1. Run all tests using Maven:
   ```
   mvnw test
   ```

2. Run a specific test class:
   ```
   mvnw test -Dtest=ClassName
   ```

3. Run a specific test method:
   ```
   mvnw test -Dtest=ClassName#methodName
   ```

### Creating Tests
1. Create test classes in the `src/test/java` directory
2. Use JUnit 5 for testing (already included in dependencies)
3. Use Spring Boot Test for integration testing
4. Follow the naming convention: `*Test.java` for test classes

### Test Example
Here's a simple test for the KeyGeneratorUtils class:

```java
package com.bmdb.security.authorizationserveur.jose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

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
    }
}
```

### Testing OAuth2 Flows
For testing OAuth2 flows:
1. Use `@SpringBootTest` to load the application context
2. Use `WebTestClient` or `TestRestTemplate` for HTTP requests
3. Use `@MockBean` to mock external dependencies
4. Configure test clients in your test configuration

## Additional Development Information

### Project Structure
- `src/main/java/com/bmdb/security/authorizationserveur/`: Main source code
  - `authentication/`: Custom authentication providers and tokens
  - `config/`: Configuration classes
  - `federation/`: Federation identity providers
  - `jose/`: JSON Object Signing and Encryption utilities
  - `web/`: Web controllers and endpoints

### Code Style
- Follow standard Java code style conventions
- Use 4 spaces for indentation
- Use camelCase for variable and method names
- Use PascalCase for class names
- Add JavaDoc comments for public classes and methods

### OAuth2 Implementation Details
This project implements an OAuth2 Authorization Server with:
- Authorization Code flow
- Client Credentials flow
- Device Code flow
- Refresh Token flow
- Token Exchange flow
- OpenID Connect support

### Security Considerations
- Keep client secrets secure
- Use HTTPS in production
- Regularly rotate keys
- Implement proper token validation
- Consider using a separate database for production instead of the embedded H2

### Debugging
- Enable debug logging by adding to application.properties:
  ```
  logging.level.org.springframework.security=DEBUG
  ```
- Use Spring Boot Actuator for monitoring (add the dependency if needed)

### Working with JWKs
The project uses JWKs (JSON Web Keys) for signing tokens:
- RSA keys are generated at startup
- Keys are managed by the `Jwks` and `KeyGeneratorUtils` classes
- For production, consider using a key store or external key management service

### Extending the Authorization Server
To add custom functionality:
1. Create custom token customizers by implementing `OAuth2TokenCustomizer`
2. Create custom authentication providers by extending `AuthenticationProvider`
3. Add custom endpoints by creating new controllers
4. Register custom clients in `AuthorizationServerConfig`