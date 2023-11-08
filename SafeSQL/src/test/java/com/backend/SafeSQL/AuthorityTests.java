package com.backend.SafeSQL;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.backend.SafeSQL.model.Authority;

public class AuthorityTests {

    @Test
    public void getAuthority_ReturnsAuthority() {
        // Arrange
        String authority = "ROLE_USER";
        Authority auth = new Authority(authority);

        // Act
        String result = auth.getAuthority();

        // Assert
        assertEquals(authority, result);
    }

    @Test
    public void getAuthority_EmptyAuthority() {
        // Arrange
        String authority = "";
        Authority auth = new Authority(authority);

        // Act
        String result = auth.getAuthority();

        // Assert
        assertEquals(authority, result);
    }

    @Test
    public void getAuthority_NullAuthority() {
        // Arrange
        String authority = null;
        Authority auth = new Authority(authority);

        // Act
        String result = auth.getAuthority();

        // Assert
        assertNull(result);
    }

    @Test
    public void getAuthority_WhitespaceAuthority() {
        // Arrange
        String authority = "  ";
        Authority auth = new Authority(authority);

        // Act
        String result = auth.getAuthority();

        // Assert
        assertEquals(authority, result);
    }
}
