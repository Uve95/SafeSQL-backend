package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

public class UserRolTests {

    private UserRol userRol;

    @BeforeEach
    public void setUp() {
        userRol = new UserRol();
    }

    @Test
    public void getUserRolId_ReturnsUserRolId() {
        // Arrange
        Long expectedUserRolId = 1L;
        userRol.setUserRolId(expectedUserRolId);

        // Act
        Long actualUserRolId = userRol.getUserRolId();

        // Assert
        assertEquals(expectedUserRolId, actualUserRolId);
    }

    @Test
    public void setUserRolId_SetsUserRolId() {
        // Arrange
        Long expectedUserRolId = 2L;

        // Act
        userRol.setUserRolId(expectedUserRolId);

        // Assert
        assertEquals(expectedUserRolId, userRol.getUserRolId());
    }

    @Test
    public void getUser_ReturnsUser() {
        // Arrange
        User expectedUser = new User();
        userRol.setUser(expectedUser);

        // Act
        User actualUser = userRol.getUserrio();

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void setUser_SetsUser() {
        // Arrange
        User expectedUser = new User();

        // Act
        userRol.setUser(expectedUser);

        // Assert
        assertEquals(expectedUser, userRol.getUserrio());
    }

    @Test
    public void getRol_ReturnsRol() {
        // Arrange
        Rol expectedRol = new Rol(1L, "USER");
        userRol.setRol(expectedRol);

        // Act
        Rol actualRol = userRol.getRol();

        // Assert
        assertEquals(expectedRol, actualRol);
    }

    @Test
    public void setRol_SetsRol() {
        // Arrange
        Rol expectedRol = new Rol(2L, "ADMIN");

        // Act
        userRol.setRol(expectedRol);

        // Assert
        assertEquals(expectedRol, userRol.getRol());
    }
}
