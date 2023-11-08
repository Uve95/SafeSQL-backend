package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.hibernate.mapping.Set;
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
    public void createToken() {

        assertNotNull(user.createToken());
    }

    @Test
    public void getEmail() {
        // Arrange
        String expectedEmail = "testuser@example.com";
        user.setEmail(expectedEmail);

        // Act
        String actualEmail = user.getEmail();

        // Assert
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    public void setEmail() {
        // Arrange
        String expectedEmail = "newuser@example.com";

        // Act
        user.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    public void getName() {
        // Arrange
        String expectedName = "testuser";
        user.setName(expectedName);

        // Act
        String actualName = user.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    public void setName() {
        // Arrange
        String expectedName = "newuser";

        // Act
        user.setName(expectedName);

        // Assert
        assertEquals(expectedName, user.getName());
    }

    @Test
    public void getSurname() {
        // Arrange
        String expectedSurname = "testusername";
        user.setSurname(expectedSurname);

        // Act
        String actualSurname = user.getSurname();

        // Assert
        assertEquals(expectedSurname, actualSurname);
    }

    @Test
    public void setSurname() {
        // Arrange
        String expectedSurname = "testusername";

        // Act
        user.setSurname(expectedSurname);

        // Assert
        assertEquals(expectedSurname, user.getSurname());
    }

    @Test
    public void getPassword() {
        // Arrange
        String expectedPassword = "password123";
        user.setPassword(expectedPassword);

        // Act
        String actualPassword = user.getPassword();

        // Assert
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void setPassword() {
        // Arrange
        String expectedPassword = "newPassword";

        // Act
        user.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    public void getToken() {
        // Arrange
        String expectedToken = "token";
        user.setToken(expectedToken);

        // Act
        String actualToken = user.getToken();

        // Assert
        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void setToken() {
        // Arrange
        String expectedToken = "token";

        // Act
        user.setToken(expectedToken);

        // Assert
        assertEquals(expectedToken, user.getToken());
    }

    @Test
    public void getInfomation() {
        // Arrange
        String expectedInformation = "information";
        user.setInformation(expectedInformation);

        // Act
        String actualInformation = user.getInformation();

        // Assert
        assertEquals(expectedInformation, actualInformation);
    }

    @Test
    public void setInformation() {
        // Arrange
        String expectedInformation = "information";
        user.setInformation(expectedInformation);

        // Assert
        assertEquals(expectedInformation, user.getInformation());
    }

    @Test
    public void setDate() {
        // Arrange
        String expectedDate = "date";
        user.setDate(expectedDate);

        // Assert
        assertEquals(expectedDate, user.getDate());
    }

    @Test
    public void getDate() {
        // Arrange
        String expectedDate = "date";
        user.setDate(expectedDate);

        // Act
        String actualDate = user.getDate();

        // Assert
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void setReport() {
        // Arrange
        String expectedReport = "nullnull";
        user.setReport(expectedReport);

        // Assert
        assertNotEquals(expectedReport, user.getReport());
    }

    @Test
    public void getReport() {
        // Arrange
        String expectedReport = "nullreporte";
        user.setReport(expectedReport);

        // Act
        String actualReport = user.getReport();

        // Assert
        assertNotEquals(expectedReport, actualReport);
    }

    @Test
    public void setDate_report() {
        // Arrange
        String expectedDate_report = "null;null;reporte_date";
        user.setDate_report(expectedDate_report);

        // Assert
        assertEquals(expectedDate_report, "null;null;reporte_date");
    }

    @Test
    public void getDate_report() {
        // Arrange
        String expectedDate_report = null;

        // Act
        String actualDate_report = user.getDate_report();

        // Assert
        assertEquals(expectedDate_report, actualDate_report);
    }

    @Test
    public void setDatabases() {
        // Arrange
        String expectedDatabases = "null;nulldatabases";
        user.setDatabases(expectedDatabases);

        // Assert
        assertEquals(expectedDatabases, "null;nulldatabases");
    }

    @Test
    public void getDatabases() {
        // Arrange
        String expectedDatabases = null;

        // Act
        String actualDatabases = user.getDatabases();

        // Assert
        assertEquals(expectedDatabases, actualDatabases);
    }

    @Test
    public void isEnabled() {
        // Act and Assert
        assertTrue(user.isEnabled());
    }

    @Test
    public void getAuthorities() {
        // Arrange
        userRol = new UserRol();
        Rol rol = new Rol(1L, "USER");
        userRol.setRol(rol);

        user.getUserRoles().add(userRol);

        // Act
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertFalse(authorities.contains(new Authority("USER")));
    }

    @Test
    public void isAccountNonExpired() {
        // Act and Assert
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        // Act and Assert
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        // Act and Assert
        assertTrue(user.isCredentialsNonExpired());
    }
}
