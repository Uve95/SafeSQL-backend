package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.ArrayList;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.SafeSQL.configuration.JwtUtil;
import com.backend.SafeSQL.controller.AuthenticationController;
import com.backend.SafeSQL.model.JwtRequest;
import com.backend.SafeSQL.model.JwtResponse;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.UserDetailsServiceImpl;

import jakarta.mail.Message;

public class AuthenticationControllerTests {

    private static final com.backend.SafeSQL.model.JwtRequest a = null;

    private static final Class DisabledException = null;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generarToken_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest("testuser@example.com", "password123");
        UserDetails userDetails = new User("testuser@example.com", "password123", new ArrayList<>());

        // Utiliza doReturn para evitar el problema con el método que devuelve null
        doReturn(null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        when(userDetailsService.loadUserByUsername(jwtRequest.getEmail())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("generatedToken");

        // Act
        ResponseEntity<JwtResponse> responseEntity = (ResponseEntity<JwtResponse>) authenticationController
                .generarToken(jwtRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        JwtResponse jwtResponse = responseEntity.getBody();
        assertNotNull(jwtResponse);
        assertEquals("generatedToken", jwtResponse.getToken());
    }

    @Test
public void generarToken_InvalidCredentials_ThrowsException() throws Exception {
    // Arrange
    JwtRequest jwtRequest = new JwtRequest("testuser@example.com", "invalidPassword");
    doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.generarToken(jwtRequest));
}

@Test
public void generarToken_DisabledUser_ThrowsException() throws Exception {
    // Arrange
    JwtRequest jwtRequest = new JwtRequest("disableduser@example.com", "password123");
    doThrow(new DisabledException("User is disabled")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.generarToken(jwtRequest));
}

@Test
public void getUserActual_ReturnsCurrentUser() {
    // Arrange
    Principal principal = () -> "testuser@example.com";
    User expectedUser = new User("testuser@example.com", "password123", new ArrayList<>());
    when(userDetailsService.loadUserByUsername(principal.getName())).thenReturn(expectedUser);

    // Act
    User currentUser = authenticationController.getUserActual(principal);

    // Assert
    assertNotNull(currentUser);
    assertEquals(expectedUser.getUsername(), currentUser.getUsername());
    // Agrega más aserciones según las propiedades que debes comparar
}

@Test
public void getUserActual_UserNotFound_ThrowsException() {
    // Arrange
    Principal principal = () -> "nonexistentuser@example.com";
    when(userDetailsService.loadUserByUsername(principal.getName())).thenThrow(new UsernameNotFoundException("User not found"));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.getUserActual(principal));
}


@Test
public void generarToken_AuthenticationException_ThrowsException() throws Exception {
    // Arrange
    JwtRequest jwtRequest = new JwtRequest("testuser@example.com", "password123");
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new DisabledException("Authentication failed"));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.generarToken(jwtRequest));
}

@Test
public void autenticar_UserDisabled_ThrowsException() {
    // Arrange
    String email = "disableduser@example.com";
    String password = "password123";
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new DisabledException("User is disabled"));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.autenticar(email, password));
}

@Test
public void autenticar_BadCredentials_ThrowsException() {
    // Arrange
    String email = "testuser@example.com";
    String password = "invalidPassword";
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

    // Act and Assert
    assertThrows(Exception.class, () -> authenticationController.autenticar(email, password));
}

}