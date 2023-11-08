package com.backend.SafeSQL;
import com.backend.SafeSQL.controller.AdminController;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AdminControllerTests {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_ReturnsListOfUsers() throws Exception {
        // Arrange
        List<User> userList = new ArrayList<>();
        when(adminService.list()).thenReturn(userList);

        // Act
        List<User> result = adminController.findAll();

        // Assert
        assertEquals(userList, result);
    }

    @Test
    void details_ReturnsUserDetails() throws Exception {
        // Arrange
        String token = "exampleToken";
        User user = new User();
        when(adminService.details(eq(token))).thenReturn(user);

        // Act
        ResponseEntity<?> responseEntity = adminController.details(token);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void update_ReturnsHttpStatusOK() throws Exception {
        // Arrange
        User updatedUser = new User();
        updatedUser.setPassword("newPassword"); // Password is not empty
        String email = "exampleEmail";
        
        BCryptPasswordEncoder realBCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = realBCryptPasswordEncoder.encode(updatedUser.getPassword());
        when(bCryptPasswordEncoder.encode(any(CharSequence.class))).thenReturn(hashedPassword);
    
        // Act
        ResponseEntity<?> responseEntity = adminController.update(updatedUser, email);
    
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    

    @Test
    void update_PasswordEmpty_ReturnsHttpStatusOK() throws Exception {
        // Arrange
        User updatedUser = new User();
        updatedUser.setPassword(""); // Password is empty
        String email = "exampleEmail";

        // Act
        ResponseEntity<?> responseEntity = adminController.update(updatedUser, email);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ReturnsHttpStatusOK() throws Exception {
        // Arrange
        String email = "exampleEmail";

        // Act
        ResponseEntity<?> responseEntity = adminController.delete(email);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ThrowsException_ReturnsHttpStatusOK() throws Exception {
        // Arrange
        String email = "exampleEmail";
        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN)).when(adminService).deleteUser(eq(email));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminController.delete(email));
    }
}
