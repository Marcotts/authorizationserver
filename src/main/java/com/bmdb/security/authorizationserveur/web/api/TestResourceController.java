package com.bmdb.security.authorizationserveur.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class TestResourceController {

    // Simulation d'une base de données en mémoire
    private final Map<Long, Message> messages = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('SCOPE_message.read')")
    public Map<String, Object> test(JwtAuthenticationToken token) {
        return Map.of(
                "message", "Token valide !",
                "scopes", token.getAuthorities(),
                "subject", token.getName(),
                "claims", token.getToken().getClaims()
        );
    }

    @GetMapping("/messages")
    @PreAuthorize("hasAuthority('SCOPE_message.read')")
    public ResponseEntity<List<Message>> getMessages(JwtAuthenticationToken token) {
        List<Message> messageList = messages.values().stream().toList();
        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/messages")
    @PreAuthorize("hasAuthority('SCOPE_message.write')")
    public ResponseEntity<Message> createMessage(
            @RequestBody CreateMessageRequest request,
            JwtAuthenticationToken token) {

        Long id = idGenerator.incrementAndGet();
        Message message = new Message(
                id,
                request.content(),
                token.getName(),
                Instant.now()
        );

        messages.put(id, message);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/messages/{id}")
    @PreAuthorize("hasAuthority('SCOPE_message.read')")
    public ResponseEntity<Message> getMessage(
            @PathVariable Long id,
            JwtAuthenticationToken token) {

        Message message = messages.get(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(message);
    }

    @PutMapping("/messages/{id}")
    @PreAuthorize("hasAuthority('SCOPE_message.write')")
    public ResponseEntity<Message> updateMessage(
            @PathVariable Long id,
            @RequestBody CreateMessageRequest request,
            JwtAuthenticationToken token) {

        Message existingMessage = messages.get(id);
        if (existingMessage == null) {
            return ResponseEntity.notFound().build();
        }

        Message updatedMessage = new Message(
                id,
                request.content(),
                existingMessage.author(),
                existingMessage.createdAt(),
                Instant.now()
        );

        messages.put(id, updatedMessage);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/messages/{id}")
    @PreAuthorize("hasAuthority('SCOPE_message.write')")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long id,
            JwtAuthenticationToken token) {

        if (messages.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('SCOPE_openid') or hasAuthority('SCOPE_message.read')")
    public ResponseEntity<Map<String, Object>> getProfile(JwtAuthenticationToken token) {
        return ResponseEntity.ok(Map.of(
                "sub", token.getName(),
                "scopes", token.getAuthorities(),
                "iat", token.getToken().getIssuedAt(),
                "exp", token.getToken().getExpiresAt(),
                "iss", token.getToken().getIssuer()
        ));
    }

    @PostMapping("/test-token")
    public ResponseEntity<Map<String, Object>> testToken(
            @RequestHeader("Authorization") String authHeader,
            JwtAuthenticationToken token) {

        return ResponseEntity.ok(Map.of(
                "status", "Token reçu avec succès",
                "authHeader", authHeader,
                "tokenValid", true,
                "subject", token.getName(),
                "timestamp", Instant.now()
        ));
    }

    // Classes internes pour les DTOs
    public record CreateMessageRequest(String content) {}

    public record Message(
            Long id,
            String content,
            String author,
            Instant createdAt,
            Instant updatedAt
    ) {
        // Constructeur pour création (sans updatedAt)
        public Message(Long id, String content, String author, Instant createdAt) {
            this(id, content, author, createdAt, null);
        }
    }
}
