package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.backend.SafeSQL.model.JwtResponse;

public class JwtResponseTests {

    public void getToken_ReturnsToken() {
        // Arrange
        String token = "sampleToken";
        JwtResponse jwtResponse = new JwtResponse(token);

        // Act
        String result = jwtResponse.getToken();

        // Assert
        assertEquals(token, result);
    }

    @Test
    public void setToken_SetsToken() {
        // Arrange
        JwtResponse jwtResponse = new JwtResponse();

        // Act
        jwtResponse.setToken("newToken");

        // Assert
        assertEquals("newToken", jwtResponse.getToken());
    }

    @Test
    public void setToken_NullToken() {
        // Arrange
        JwtResponse jwtResponse = new JwtResponse();

        // Act
        jwtResponse.setToken(null);

        // Assert
        assertNull(jwtResponse.getToken());
    }
}

