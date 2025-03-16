package ru.mrrex.rentcar.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTests {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtServiceImpl jwtService;

    private static final String JWT_SIGNING_KEY = "a-very-very-secret-jwt-signing-key-placed-here";
    private static final String USERNAME = "JohnDoe";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "jwtSigningKey", JWT_SIGNING_KEY);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    public void testGenerateToken_WithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(extraClaims, userDetails);

        assertNotNull(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        assertEquals(USERNAME, claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));

        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    public void testGenerateToken_WithoutExtraClaims() {
        when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        assertEquals(USERNAME, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(userDetails);
        
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testIsTokenValid_ExpiredToken() {
        String token = Jwts.builder()
            .subject(USERNAME)
            .issuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))
            .expiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 12))
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact();

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testExtractUsername() {
        when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(userDetails);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(USERNAME, extractedUsername);
    }
}
