package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.backend.SafeSQL.model.JwtRequest;

public class JwtRequestTests {

    @Test
    public void getEmail_ReturnsEmail() {
        // Arrange
        String email = "testuser@example.com";
        JwtRequest jwtRequest = new JwtRequest(email, "password123");

        // Act
        String result = jwtRequest.getEmail();

        // Assert
        assertEquals(email, result);
    }

    @Test
    public void getPassword_ReturnsPassword() {
        // Arrange
        String password = "password123";
        JwtRequest jwtRequest = new JwtRequest("testuser@example.com", password);

        // Act
        String result = jwtRequest.getPassword();

        // Assert
        assertEquals(password, result);
    }

    @Test
    public void setEmail_SetsEmail() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();

        // Act
        jwtRequest.setEmail("newuser@example.com");

        // Assert
        assertEquals("newuser@example.com", jwtRequest.getEmail());
    }

    @Test
    public void setPassword_SetsPassword() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();

        // Act
        jwtRequest.setPassword("newPassword");

        // Assert
        assertEquals("newPassword", jwtRequest.getPassword());
    }

    @Test
    public void setEmail_NullEmail() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();

        // Act
        jwtRequest.setEmail(null);

        // Assert
        assertNull(jwtRequest.getEmail());
    }

    @Test
    public void setPassword_NullPassword() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();

        // Act
        jwtRequest.setPassword(null);

        // Assert
        assertNull(jwtRequest.getPassword());
    }
}
