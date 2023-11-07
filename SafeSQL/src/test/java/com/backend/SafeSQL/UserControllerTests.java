package com.backend.SafeSQL;

import com.backend.SafeSQL.controller.UserController;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Asegúrate de que esta dependencia esté presente.

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerUser_ReturnsUser() throws Exception {
        // Arrange
        User user = new User();
        when(userService.saveUser(any(User.class), anySet())).thenReturn(user);

        // Act
        User result = userController.register(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void updateUser_ReturnsHttpStatusOK() throws Exception {
        // Arrange
        User user = new User();
        user.setPassword("password"); // Establece una contraseña no nula
        String token = "exampleToken";
        when(userService.updateUser(any(User.class), eq(token))).thenReturn(user);
    
        // Act
        ResponseEntity<?> responseEntity = userController.update(user, token);
    
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    

    @Test
    void updateUser_HandleEmptyPassword() throws Exception {
        // Arrange
        User user = new User();
        user.setPassword(""); // Password empty
        String token = "exampleToken";

        // Simulate the exception thrown by userService.updateUser
        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN)).when(userService).updateUser(any(User.class),
                eq(token));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.update(user, token));
    }

    @Test
    void forgotPassword_Success() {
        // Arrange
        User user = new User();

        // Act and Assert
        assertDoesNotThrow(() -> userController.forgotPassword(user));
    }

    @Test
    void changePassword_Success() {
        // Arrange
        User user = new User();

        // Act and Assert
        assertDoesNotThrow(() -> userController.changePassword(user));
    }

    @Test
    void connectBD_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.connectBD(info));
    }

    @Test
    void connectBD_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.connectBD(eq(info))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.connectBD(info));
    }

    @Test
    void bdName_Success() {
        // Arrange
        String email = "example@email.com";

        // Act and Assert
        assertDoesNotThrow(() -> userController.bdName(email));
    }

    @Test
    void bdName_Failure() throws Exception {
        // Arrange
        String email = "example@email.com";
        when(userService.bdName(eq(email))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.bdName(email));
    }

    @Test
    void checklistConfiguration_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistConfiguration(info));
    }

    @Test
    void checklistConfiguration_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistConfig(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistConfiguration(info));
    }

    @Test
    void checklistNetwork_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistNetwork(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistNetwork(info));
    }

    @Test
    void checklistPermission_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistPermission(info));
    }

    @Test
    void checklistPermission_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistPermission(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistPermission(info));
    }

    @Test
    void checklistPassword_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistPassword(info));
    }

    @Test
    void checklistPassword_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistPassword(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistPassword(info));
    }

    @Test
    void checklistSession_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistSession(info));
    }

    @Test
    void checklistSession_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistSession(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistSession(info));
    }

    @Test
    void checklistMaintenance_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistMaintenance(info));
    }

    @Test
    void checklistMaintenance_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistMaintenance(eq(info)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistMaintenance(info));
    }

    @Test
    void checklistData_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistData(info));
    }

    @Test
    void checklistData_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistData(eq(info))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistData(info));
    }

    @Test
    void checklistRol_Success() {
        // Arrange
        String[] info = new String[72];

        // Act and Assert
        assertDoesNotThrow(() -> userController.checklistRol(info));
    }

    @Test
    void checklistRol_Failure() throws Exception {
        // Arrange
        String[] info = new String[72];
        when(userService.checklistRol(eq(info))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.checklistRol(info));
    }

    @Test
    void getToken_Success() {
        // Arrange
        String email = "example@email.com";

        // Act and Assert
        assertDoesNotThrow(() -> userController.getToken(email));
    }

    @Test
    void getToken_Failure() throws Exception {
        // Arrange
        String email = "example@email.com";
        when(userService.getToken(eq(email))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.getToken(email));
    }

    @Test
    void deleteInfo_Success() {
        // Arrange
        String info = "example info";

        // Act and Assert
        assertDoesNotThrow(() -> userController.deleteInfo(info));
    }

    @Test
    void setTime_Success() {
        // Arrange
        String[] info = new String[2];

        // Act and Assert
        assertDoesNotThrow(() -> userController.setTime(info));
    }

    @Test
    void getTime_Success() {
        // Arrange
        String email = "example@email.com";

        // Act and Assert
        assertDoesNotThrow(() -> userController.getTime(email));
    }

    @Test
    void getTime_Failure() throws Exception {
        // Arrange
        String email = "example@email.com";
        when(userService.getTime(eq(email))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.getTime(email));
    }

    @Test
    void setReport_Success() {
        // Arrange
        String[] info = new String[2];

        // Act and Assert
        assertDoesNotThrow(() -> userController.setReport(info));
    }

    @Test
    void getReport_Success() {
        // Arrange
        String email = "example@email.com";

        // Act and Assert
        assertDoesNotThrow(() -> userController.getReport(email));
    }

    @Test
    void getReport_Failure() throws Exception {
        // Arrange
        String email = "example@email.com";
        when(userService.getReport(eq(email))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.getReport(email));
    }

    @Test
    void getInfo_Success() {
        // Arrange
        String email = "example@email.com";

        // Act and Assert
        assertDoesNotThrow(() -> userController.getInfo(email));
    }

    @Test
    void getInfo_Failure() throws Exception {
        // Arrange
        String email = "example@email.com";
        when(userService.getInfo(eq(email))).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Error"));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userController.getInfo(email));
    }
}
