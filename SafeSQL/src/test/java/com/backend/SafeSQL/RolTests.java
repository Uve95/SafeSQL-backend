package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.SafeSQL.model.Rol;

public class RolTests {

    private Rol rol;

    @BeforeEach
    public void setUp() {
        rol = new Rol();
    }

    @Test
    public void getRolId_ReturnsRolId() {
        // Arrange
        Long expectedRolId = 1L;
        rol.setRolId(expectedRolId);

        // Act
        Long actualRolId = rol.getRolId();

        // Assert
        assertEquals(expectedRolId, actualRolId);
    }

    @Test
    public void setRolId_SetsRolId() {
        // Arrange
        Long expectedRolId = 2L;

        // Act
        rol.setRolId(expectedRolId);

        // Assert
        assertEquals(expectedRolId, rol.getRolId());
    }

    @Test
    public void getRolName_ReturnsRolName() {
        // Arrange
        String expectedRolName = "USER";
        rol.setRolName(expectedRolName);

        // Act
        String actualRolName = rol.getRolName();

        // Assert
        assertEquals(expectedRolName, actualRolName);
    }

    @Test
    public void setRolName_SetsRolName() {
        // Arrange
        String expectedRolName = "ADMIN";

        // Act
        rol.setRolName(expectedRolName);

        // Assert
        assertEquals(expectedRolName, rol.getRolName());
    }
}

