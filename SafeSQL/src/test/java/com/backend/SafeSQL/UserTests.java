package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;

import com.backend.SafeSQL.model.Authority;
import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

public class UserTests {

    private User user;

    @Mock
    private UserRol userRol;

    @BeforeEach
    public void setUp() {
        user = new User(null, null, null);
    }

    @Test
    public void getEmail_ReturnsEmail() {
        // Arrange
        String expectedEmail = "testuser@example.com";
        user.setEmail(expectedEmail);

        // Act
        String actualEmail = user.getEmail();

        // Assert
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    public void setEmail_SetsEmail() {
        // Arrange
        String expectedEmail = "newuser@example.com";

        // Act
        user.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    public void getPassword_ReturnsPassword() {
        // Arrange
        String expectedPassword = "password123";
        user.setPassword(expectedPassword);

        // Act
        String actualPassword = user.getPassword();

        // Assert
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void setPassword_SetsPassword() {
        // Arrange
        String expectedPassword = "newPassword";

        // Act
        user.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    public void getUsername_ReturnsEmail() {
        // Arrange
        String expectedEmail = "testuser@example.com";
        user.setEmail(expectedEmail);

        // Act
        String actualUsername = user.getUsername();

        // Assert
        assertEquals(expectedEmail, actualUsername);
    }

    @Test
    public void isEnabled_ReturnsTrue() {
        // Act and Assert
        assertTrue(user.isEnabled());
    }

    @Test
    public void getAuthorities_ReturnsAuthorities() {
        // Arrange
        userRol = new UserRol();
        Rol rol = new Rol(1L, "USER");
        userRol.setRol(rol);

        user.getUserRoles().add(userRol);

        // Act
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new Authority("USER")));
    }
}
