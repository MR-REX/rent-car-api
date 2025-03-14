package ru.mrrex.rentcar.services;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);
}
